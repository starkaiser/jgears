/*
 * Copyright (C) 2021 Sorin Cătălin Păștiță
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.java.jgears.gui;
/**
 *
 * @author Sorin Cătălin Păștiță
 */
import com.sun.javafx.scene.CssFlags;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.File;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;
import main.java.jgears.csg.jcsg.CSG;
import main.java.jgears.csg.jcsg.MeshContainer;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import org.reactfx.Subscription;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.codehaus.groovy.control.CompilationFailedException;

public class ScriptController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private RadioButton addRadioBtn, clearRadioBtn;
    
    private final ToggleGroup adddToggle = new ToggleGroup();

    private CSG csgObject;

    private static final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private static final String sampleCode = String.join("\n", new String[] {
            "double maxR = 10;",
            "",
            "double w = 30;",
            "double h = 30;",
            "double d = 30;",
            "",
            "CSG.setDefaultOptType(CSG.OptType.POLYGON_BOUND);",
            "",
            "CSG spheres = null;",
            "",
            "",
            "for(int i = 0;i<70;i++) {",
            "   CSG s = new Sphere(Math.random()*maxR).toCSG().transformed(Transform.unity().translate(Math.random()*w,",
            "                                                                                           Math.random()*h,",
            "                                                                                           Math.random()*d));",
            "   if (spheres == null) {",
            "       spheres = s;",
            "   } else {",
            "       spheres = spheres.union(s);",
            "   }",
            "}",
            "return spheres;",
    });

    private CodeArea codeArea;
    private ExecutorService executor;

    private double x = 0, y = 0;
    private Stage stage;
    private Group partsGroup;
    private List<CSG> partsCSG;

    private boolean autoCompile = false;
    
    @FXML
    private void handleOpen(ActionEvent event) {
	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Groovy Script");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Groovy files", "*.groovy"));

        File f = fileChooser.showOpenDialog(null);

        if (f == null) {
            return;
        }

        String fName = f.getAbsolutePath();

        if (!fName.toLowerCase().endsWith(".groovy")) {
            fName += ".groovy";
        }

        try {
            setCode(new String(Files.readAllBytes(Paths.get(fName)), "UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(ScriptController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void handleSave(ActionEvent event) {
	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Groovy Script");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Groovy files","*.groovy"));

        File f = fileChooser.showSaveDialog(null);

        if (f == null) {
            return;
        }

        String fName = f.getAbsolutePath();

        if (!fName.toLowerCase().endsWith(".groovy")) {
            fName += ".groovy";
        }

        try {
            Files.write(Paths.get(fName), getCode().getBytes("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(ScriptController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleOk(ActionEvent event) {// TODO
	compile(getCode());
	executor.shutdown();
        ((Stage)rootPane.getScene().getWindow()).close();
    }
    @FXML
    private void handleClose (ActionEvent event) {
        executor.shutdown();
        ((Stage)rootPane.getScene().getWindow()).close();
    }
    @FXML
    private void handleRun(ActionEvent event) {
        compile(getCode());
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //makeDraggable();
	adddToggle.getToggles().addAll(addRadioBtn, clearRadioBtn);
	addRadioBtn.setSelected(true);

        executor = Executors.newSingleThreadExecutor();
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        Subscription cleanupWhenDone = codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(codeArea.multiPlainChanges())
                .filterMap(t -> {
                    if(t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);

        // call when no longer need it: `cleanupWhenFinished.unsubscribe();`

        codeArea.replaceText(0, 0, sampleCode);
        borderPane.setCenter(new VirtualizedScrollPane<>(codeArea));
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private void compile(String code) {
        csgObject = null;

	if(clearRadioBtn.isSelected())
	    partsGroup.getChildren().clear();

        try {
            CompilerConfiguration cc = new CompilerConfiguration();

            cc.addCompilationCustomizers(
                    new ImportCustomizer().addStarImports("main.java.jgears.csg.jcsg","main.java.jgears.csg.jcsg.samples", 
							  "main.java.jgears.csg.vvecmath").
					   addStaticStars("main.java.jgears.csg.vvecmath.Transform"));

	    Binding binding = new Binding();
	    binding.setVariable("partsCSG", partsCSG);
	    
            GroovyShell shell = new GroovyShell(getClass().getClassLoader(),
                    binding, cc);

            Script script = shell.parse(code);

            Object obj = script.run();

            if (obj instanceof CSG) {

                CSG csg = (CSG) obj;

                csgObject = csg;		
	
		partsGroup.getChildren().add(generateMeshView(csg));
            } else {
                System.out.println(">> no CSG object returned :(");
            }

        } catch (CompilationFailedException ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void setCode(String code) {
        codeArea.replaceText(code);
    }
    private String getCode() {
        return codeArea.getText();
    }
    private MeshView generateMeshView(CSG csg) {
        MeshContainer meshContainer = csg.toJavaFXMesh();
        final MeshView meshView = meshContainer.getAsMeshViews().get(0);
        PhongMaterial m = new PhongMaterial(Color.DEEPSKYBLUE);
        meshView.setCullFace(CullFace.NONE);
        meshView.setMaterial(m);
	return meshView;
    }
    public void setParts(Group partsGroup, List<CSG> partsCSG) {
        this.partsGroup = partsGroup;
        this.partsCSG = partsCSG;
    }
    public ExecutorService getExecutor(){
	return this.executor;
    }
}
