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
public final class ResultsUtils {
    // Class containing HTML strings used when formatting the results 
    
    // used to format the geometric results for parallel gear drives
    public static final String PARALLELGEOM = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Results</title>\n" +
            "<style>\n" +
            "td{text-align:left;font-weight: normal; font-size: 9pt; font-family: Noto Sans}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table style=\"width:95%%\">\n" +
            "  <tr>\n" +
            "    <td>p<sub>tb</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>p</td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>p<sub>t</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>a</td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>&alpha;<sub>t</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>&alpha;<sub>W</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>&alpha;<sub>tW</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d</td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>b</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>f</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>a</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>W</td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>z<sub>W</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f ul</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>M</td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>M</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>s</td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>s<sub>a</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>s<sub>c</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>h<sub>c</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>";
    
    // used tor format the strength results for parallel gear drives
    public static final String PARALLELSTRENGTH = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Results</title>\n" +
            "<style>\n" +
            "td{text-align:left;font-weight: normal; font-size: 9pt; font-family: Noto Sans}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table style=\"width:95%%\">\n" +
            "  <tr>\n" +
            "    <td>F<sub>t</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>r</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>a</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>n</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>v</td>\n" +
            "    <td style=\"text-align:right;\">%.4f m/s</td>\n" +
            "  </tr>\n" +
	    
	    "  <tr>\n" +
            "    <td><strong>Pinion</strong></td>\n" +
            "    <td style=\"text-align:right;\"> </td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "  <tr>\n" +
            "    <td>S<sub>1</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f ul</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>all1</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
	    
	    "  <tr>\n" +
            "    <td><strong>Gear</strong></td>\n" +
            "    <td style=\"text-align:right;\"> </td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "  <tr>\n" +
            "    <td>S<sub>2</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f ul</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>all2</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>";
    
    public static final String BEVELGEOM = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Results</title>\n" +
            "<style>\n" +
            "td{text-align:left;font-weight: normal; font-size: 9pt; font-family: Noto Sans}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table style=\"width:95%%\">\n" +
            "  <tr>\n" +
            "    <td>&alpha;<sub>nm</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>&alpha;<sub>t</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>&beta;<sub>b</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>&beta;<sub>e</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>&delta;</td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>&delta;<sub>a</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>&delta;<sub>f</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f deg</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>R<sub>e</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>R<sub>m</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>e</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>fe</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>ae</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>m</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>ai</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>A<sub>e</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>A<sub>i</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>h<sub>e</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>s<sub>e</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>t<sub>c</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>a<sub>c</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>z<sub>n</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>n</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>an</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>bn</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>z<sub>v</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>v</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>va</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>d<sub>vb</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f mm</td>\n" +
            "  </tr>\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>";
    
    public static final String BEVELSTRENGTH = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Results</title>\n" +
            "<style>\n" +
            "td{text-align:left;font-weight: normal; font-size: 9pt; font-family: Noto Sans}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table style=\"width:95%%\">\n" +
            "  <tr>\n" +
            "    <td>F<sub>t</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>n</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>v</td>\n" +
            "    <td style=\"text-align:right;\">%.4f mps</td>\n" +
            "  </tr>\n" +

            "  <tr>\n" +
            "    <td><strong>Pinion</strong></td>\n" +
            "    <td style=\"text-align:right;\"> </td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>r1</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>r2</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>a1</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>a2</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>S<sub>1</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f ul</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>all1</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +

            "  <tr>\n" +
            "    <td><strong>Gear</strong></td>\n" +
            "    <td style=\"text-align:right;\"> </td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>r1</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>r2</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>a1</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>a2</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>S<sub>2</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f ul</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>F<sub>all2</sub></td>\n" +
            "    <td style=\"text-align:right;\">%.4f N</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>";
    
    public static final String REPORTHEAD = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Technical Report</title>\n" +
            "<style>\n" +
            "body,p,table,th,td,h1, h2,h3{font-weight: normal; font-size: 10pt; font-family: Noto Mono}\n" +
            "body{color: #000000; background-color: #ffffff}\n" +
            "h1{font-weight: bold; font-size: 200%; margin-bottom: 1em}\n" +
            "h2{font-weight: bold; font-size: 150%; margin-bottom: 0.6em}\n" +
            "h3{font-weight: bold; font-size: 120%; margin-bottom: 0.5em}\n" +
            "p{margin: 0.5em 0em 0.3em 0em; line-height: 100%}\n" +
            "blockquote{margin: 0em 0em 2em 1em; font-size: 100%; line-height: 100%; direction: ltr}\n" +
            ".ta{border: dodgerblue 1px solid; border-collapse: collapse; background-color: #ffffff}\n" +
            ".ta td,.ta th{border: dodgerblue 1px solid; padding: 2pt; valign: top}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>";
    public static final String REPORTDRIVETYPE = "<h1>%s</h1>";
    public static final String REPORTDATE = "<p>%s</p>";
    public static final String REPORTLINE = "<hr />";
    public static final String REPORTINFO = "<h2>Project Info</h3>\n" +
            "<blockquote>\n" +
            " <p>Input Type - %s</p>\n" +
            " <p>Design Guide - %s</p>\n" +
            " <p>Drive Type - %s</p>\n" +
            " <p>Method of Strength Calculation - %s</p>\n" +
            " <p>Type of Load Calculation - %s</p>\n" +
            " <p>Type of Strength Calculation - %s</p>\n" +
            "</blockquote>";
    public static final String REPORTINFOBEVEL = "<h2>Project Info</h3>\n" +
            "<blockquote>\n" +
            " <p>Input Type - %s</p>\n" +
            " <p>Method of Strength Calculation - %s</p>\n" +
            " <p>Type of Load Calculation - %s</p>\n" +
            " <p>Type of Strength Calculation - %s</p>\n" +
            "</blockquote>";
    public static final String REPORTPARALLELCOMMON = "<h2>Geometrical Calculation</h2>\n" +
            "<h3>Common Parameters</h3>\n" +
            "<blockquote>\n" +
            "<table class=\"ta\">\n" +
            " <tr>\n" +
            "  <td>Gear Ratio</td>\n" +
            "  <td>i</td>\n" +
            "  <td align=\"middle\">%.4f ul</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Desired Gear Ratio</td>\n" +
            "  <td>i<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">in</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Module</td>\n" +
            "  <td>m</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr> \n" +
            " <tr>\n" +
            "  <td>Helix Angle</td>\n" +
            "  <td>&beta;</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Pressure Angle</td>\n" +
            "  <td>&alpha;</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Center Distance</td>\n" +
            "  <td>a<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">w</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Product Center Distance</td>\n" +
            "  <td>a</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Total Unit Correction</td>\n" +
            "  <td>&Sigma;x</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Circular Pitch</td>\n" +
            "  <td>p</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Base Circular Pitch</td>\n" +
            "  <td>p<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">tb</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Operating Pressure Angle</td>\n" +
            "  <td>&alpha;<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">w</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Contact Ratio</td>\n" +
            "  <td>&epsilon;</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            "</table>\n" +
            "</blockquote>";
    public static final String REPORTBEVELCOMMON = "<h2>Geometrical Calculation</h2>\n" +
            "<h3>Common Parameters</h3>\n" +
            "<blockquote>\n" +
            "<table class=\"ta\">\n" +
            " <tr>\n" +
            "  <td>Gear Ratio</td>\n" +
            "  <td>i</td>\n" +
            "  <td align=\"middle\">%.4f ul</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Tangential Module</td>\n" +
            "  <td>m<sub>et</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr> \n" +
            " <tr>\n" +
            "  <td>Helix Angle</td>\n" +
            "  <td>&beta;</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Tangential Pressure Angle</td>\n" +
            "  <td>&alpha;<sub>t</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Shaft Angle</td>\n" +
            "  <td>&Sigma;</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Normal Pressure Angle at End</td>\n" +
            "  <td>&alpha;<sub>ne</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Normal Pressure Angle in Middle Plane</td>\n" +
            "  <td>&alpha;<sub>nm</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Base Helix Angle</td>\n" +
            "  <td>&beta;<sub>b</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Helix Angle at End</td>\n" +
            "  <td>&beta;<sub>e</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Module</td>\n" +
            "  <td>m</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Contact Ratio</td>\n" +
            "  <td>&epsilon;</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
	    
	    " <tr>\n" +
            "  <td>Transverse Contact Ratio</td>\n" +
            "  <td>&epsilon;<sub>&alpha;</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Overlap Ratio</td>\n" +
            "  <td>&epsilon;<sub>&beta;</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
	    
	    " <tr>\n" +
            "  <td>Virtual Gear Ratio</td>\n" +
            "  <td>u<sub>v</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Equivalent Center Distance</td>\n" +
            "  <td>a<sub>v</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Virtual Center Distance</td>\n" +
            "  <td>a<sub>n</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Pitch Cone Radius</td>\n" +
            "  <td>R<sub>e</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Pitch Cone Radius in Middle Plane</td>\n" +
            "  <td>R<sub>m</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Face Width</td>\n" +
            "  <td>b</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            "</table>\n" +
            "</blockquote>";
    
    // individual parameters for the parallel drive
    public static final String REPORTPARALLELINDPARAM = "<h3>Individual Parameters</h3>\n" +
            "<blockquote>\n" +
            "<table class=\"ta\">\n" +
            " <tr>\n" +
            "  <td colspan=\"2\"></td>\n" +
            "  <td align=\"middle\">Pinion</td>\n" +
            "  <td align=\"middle\">Gear</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td colspan=\"2\">Type of model</td>\n" +
            "  <td align=\"middle\">Component</td>\n" +
            "  <td align=\"middle\">No Model</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Number of Teeth</td>\n" +
            "  <td>z</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%d ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%d ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Unit Correction</td>\n" +
            "  <td>x</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Pitch Diameter</td>\n" +
            "  <td>d</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Outside Diameter</td>\n" +
            "  <td>d<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">a</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Root Diameter</td>\n" +
            "  <td>d<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">f</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Base Circle Diameter</td>\n" +
            "  <td>d<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">b</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Work Pitch Diameter</td>\n" +
            "  <td>d<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">w</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Facewidth</td>\n" +
            "  <td>b</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Facewidth Ratio</td>\n" +
            "  <td>b<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">r</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Addendum</td>\n" +
            "  <td>a*</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Clearance</td>\n" +
            "  <td>c*</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Root Fillet</td>\n" +
            "  <td>r<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">f</span>*</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Tooth Thickness</td>\n" +
            "  <td>s</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Tangential Tooth Thickness</td>\n" +
            "  <td>s<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">t</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Chordal Thickness</td>\n" +
            "  <td>s<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">c</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Chordal Addendum</td>\n" +
            "  <td>h<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">c</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Chordal Dimension</td>\n" +
            "  <td>W</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Chordal Dimension Teeth</td>\n" +
            "  <td>z<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">w</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Dimension Over (Between) Wires</td>\n" +
            "  <td>M</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Wire Diameter</td>\n" +
            "  <td>d<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">M</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Virtual Number of Teeth</td>\n" +
            "  <td>z<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">v</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Virtual Pitch Diameter</td>\n" +
            "  <td>d<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">n</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Virtual Outside Diameter</td>\n" +
            "  <td>d<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">an</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Virtual Base Circle Diameter</td>\n" +
            "  <td>d<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">bn</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Unit Correction without Tapering</td>\n" +
            "  <td>x<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">z</span></td>\n" +
            "  <td align=\"middle\"> <FONT color=black>%.4f ul</FONT> </td>\n" +
            "  <td align=\"middle\"> <FONT color=black>%.4f ul</FONT> </td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Unit Correction without Undercut</td>\n" +
            "  <td>x<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">p</span></td>\n" +
            "  <td align=\"middle\"> <FONT color=black>%.4f ul</FONT> </td>\n" +
            "  <td align=\"middle\"> <FONT color=black>%.4f ul</FONT> </td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Unit Correction Allowed Undercut</td>\n" +
            "  <td>x<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">d</span></td>\n" +
            "  <td align=\"middle\"> <FONT color=black>%.4f ul</FONT> </td>\n" +
            "  <td align=\"middle\"> <FONT color=black>%.4f ul</FONT> </td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Addendum Truncation</td>\n" +
            "  <td>k</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Unit Outside Tooth Thickness</td>\n" +
            "  <td>s<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">a</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Tip Pressure Angle</td>\n" +
            "  <td>&alpha;<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">a</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f deg</FONT></td>\n" +
            " </tr>\n" +
            "</table>\n" +
            "</blockquote>";
    
    // individual parameters for the bevel drive
    public static final String REPORTBEVELINDPARAM = "<h3>Individual Parameters</h3>\n" +
            "<blockquote>\n" +
            "<table class=\"ta\">\n" +
            " <tr>\n" +
            "  <td colspan=\"2\"></td>\n" +
            "  <td align=\"middle\">Pinion</td>\n" +
            "  <td align=\"middle\">Gear</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td colspan=\"2\">Type of model</td>\n" +
            "  <td align=\"middle\">Component</td>\n" +
            "  <td align=\"middle\">No Model</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Number of Teeth</td>\n" +
            "  <td>z</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%d ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%d ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Unit Correction</td>\n" +
            "  <td>x</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Tangential Displacement</td>\n" +
            "  <td>x<sub>t</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
	    
	    
            " <tr>\n" +
            "  <td>Pitch Diameter at End</td>\n" +
            "  <td>d<sub>e</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Pitch Diameter in Middle Plane/td>\n" +
            "  <td>d<sub>m</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Outside Diameter at End</td>\n" +
            "  <td>d<sub>ae</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Outside Diameter at Small End</td>\n" +
            "  <td>d<sub>ai</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Root Diameter at End</td>\n" +
            "  <td>d<sub>fe</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    
            " <tr>\n" +
            "  <td>Vertex Distance</td>\n" +
            "  <td>A<sub>e</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Vertex Distance at Small End</td>\n" +
            "  <td>A<sub>i</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Pitch Cone Angle</td>\n" +
            "  <td>&delta;</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Outside Cone Angle</td>\n" +
            "  <td>&delta;<sub>a</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Root Cone Angle</td>\n" +
            "  <td>&delta;<sub>f</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Addendum</td>\n" +
            "  <td>a*</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Clearance</td>\n" +
            "  <td>c*</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Root Fillet</td>\n" +
            "  <td>r<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">f</span>*</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
	    
	    " <tr>\n" +
            "  <td>Whole Depth of Tooth</td>\n" +
            "  <td>h<sub>e</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Tooth Thickness at End</td>\n" +
            "  <td>s<sub>e</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +

            " <tr>\n" +
            "  <td>Chordal Thickness</td>\n" +
            "  <td>s<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">c</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Chordal Addendum</td>\n" +
            "  <td>h<span style=\"FONT-SIZE: smaller; VERTICAL-ALIGN: sub\">c</span></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    
	    " <tr>\n" +
            "  <td>Equivalent Number of Teeth</td>\n" +
            "  <td>z<sub>v</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Equivalent Pitch Diameter</td>\n" +
            "  <td>d<sub>v</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Equivalent Outside Diameter</td>\n" +
            "  <td>d<sub>va</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Equivalent Base Circle Diameter</td>\n" +
            "  <td>d<sub>vb</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Virtual Number of Teeth</td>\n" +
            "  <td>z<sub>n</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Virtual Pitch Diameter</td>\n" +
            "  <td>d<sub>n</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Virtual Outside Diameter</td>\n" +
            "  <td>d<sub>an</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Virtual Base Circle Diameter</td>\n" +
            "  <td>d<sub>bn</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Unit Correction without Tapering</td>\n" +
            "  <td>x<sub>z</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Unit Correction without Undercut</td>\n" +
            "  <td>x<sub>p</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Unit Correction Allowed Undercut</td>\n" +
            "  <td>x<sub>d</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Addendum Truncation</td>\n" +
            "  <td>k</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Unit Outside Tooth Thickness</td>\n" +
            "  <td>s<sub>a</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f mm</FONT></td>\n" +
            " </tr>\n" +
	    
            "</table>\n" +
            "</blockquote>";
    public static final String REPORTPARALLELSTRENGTHPARAM ="<h2>Strength Calculation</h2>\n" +
            "<h3>Results</h3>\n" +
            "<blockquote>\n" +
            "<table class=\"ta\">\n" +
            " <tr>\n" +
            "  <td colspan=\"2\"></td>\n" +
            "  <td align=\"middle\">Pinion</td>\n" +
            "  <td align=\"middle\">Gear</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td colspan=\"2\">Material Properties</td>\n" +
            "  <td align=\"middle\">%s</td>\n" +
            "  <td align=\"middle\">%s</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Allowable Bending Stress</td>\n" +
            "  <td>&sigma;<sub>Ab</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f MPa</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f MPa</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Allowable Load</td>\n" +
            "  <td>F<sub>all</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Strength Factor</td>\n" +
            "  <td>S</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Power</td>\n" +
            "  <td>P</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f kW</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f kW</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Speed</td>\n" +
            "  <td>n</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f rpm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f rpm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Torque</td>\n" +
            "  <td>M</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f Nm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f Nm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Radial Force</td>\n" +
            "  <td>F<sub>r</sub></td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Axial Force</td>\n" +
            "  <td>F<sub>a</sub></td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Tangential Force</td>\n" +
            "  <td>F<sub>t</sub></td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Normal Force</td>\n" +
            "  <td>F<sub>n</sub></td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Circumferential Speed</td>\n" +
            "  <td>v</td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f mps</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Efficiency</td>\n" +
            "  <td>&eta;</td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            "%s" +
            "</table>\n" +
            "</blockquote>";
    public static final String REPORTBEVELSTRENGTHPARAM ="<h2>Strength Calculation</h2>\n" +
            "<h3>Results</h3>\n" +
            "<blockquote>\n" +
            "<table class=\"ta\">\n" +
            " <tr>\n" +
            "  <td colspan=\"2\"></td>\n" +
            "  <td align=\"middle\">Pinion</td>\n" +
            "  <td align=\"middle\">Gear</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td colspan=\"2\">Material Properties</td>\n" +
            "  <td align=\"middle\">%s</td>\n" +
            "  <td align=\"middle\">%s</td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Allowable Bending Stress</td>\n" +
            "  <td>&sigma;<sub>Ab</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f MPa</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f MPa</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Allowable Load</td>\n" +
            "  <td>F<sub>all</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Strength Factor</td>\n" +
            "  <td>S</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Power</td>\n" +
            "  <td>P</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f kW</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f kW</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Speed</td>\n" +
            "  <td>n</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f rpm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f rpm</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Torque</td>\n" +
            "  <td>M</td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f Nm</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f Nm</FONT></td>\n" +
            " </tr>\n" +
	    
            " <tr>\n" +
            "  <td>Radial Force (direction 1)</td>\n" +
            "  <td>F<sub>r1</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Radial Force (direction 2)</td>\n" +
            "  <td>F<sub>r2</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
	    
	    " <tr>\n" +
            "  <td>Axial Force (direction 1)</td>\n" +
            "  <td>F<sub>a1</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Axial Force (direction 2)</td>\n" +
            "  <td>F<sub>a2</sub></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            "  <td align=\"middle\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
	    
	    
	    
	    " <tr>\n" +
            "  <td>Tangential Force</td>\n" +
            "  <td>F<sub>t</sub></td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Normal Force</td>\n" +
            "  <td>F<sub>n</sub></td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f N</FONT></td>\n" +
            " </tr>\n" +
            " <tr>\n" +
            "  <td>Circumferential Speed</td>\n" +
            "  <td>v</td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f mps</FONT></td>\n" +
            " </tr>\n" +
	    " <tr>\n" +
            "  <td>Efficiency</td>\n" +
            "  <td>&eta;</td>\n" +
            "  <td align=\"middle\" colspan=\"2\"><FONT color=black>%.4f ul</FONT></td>\n" +
            " </tr>\n" +
            "%s" +
            "</table>\n" +
            "</blockquote>";
    public static final String REPORTEND = "</body>\n" +
            "</html>";
    public static final String REPORTPOSITIVECHECK = " <tr>\n" +
            "  <td colspan=\"2\">Check Calculation</td>\n" +
            "  <td style=\"color:green\" align=\"middle\" colspan=\"2\">Positive</td>\n" +
            " </tr>\n";
    public static final String REPORTNEGATIVECHECK = " <tr>\n" +
            "  <td colspan=\"2\">Check Calculation</td>\n" +
            "  <td style=\"color:red\" align=\"middle\" colspan=\"2\">Negative</td>\n" +
            " </tr>\n";
}
