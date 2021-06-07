double maxR = 10;

double w = 30;
double h = 30;
double d = 30;

CSG.setDefaultOptType(CSG.OptType.POLYGON_BOUND);

CSG spheres = null;


for(int i = 0;i<70;i++) {
   CSG s = new Sphere(Math.random()*maxR).toCSG().transformed(Transform.unity().translate(Math.random()*w,
                                                                                           Math.random()*h,
                                                                                           Math.random()*d));
   if (spheres == null) {
       spheres = s;
   } else {
       spheres = spheres.union(s);
   }
}
return spheres;