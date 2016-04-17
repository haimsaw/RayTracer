package RayTracing;
import java.util.List;

public abstract class Surface {

    Material material;

    public Surface(Material material) {
        this.material = material;
    }

    public abstract List<Intersection> getIntersections(Ray ray);

    public MyVector getNormalToDirection(MyVector point, MyVector directionToRayStart){
            MyVector normal =this.getNormal(point);
            if (Math.acos(normal.getCosAngel(directionToRayStart))>90){
                return normal.multiply(-1);
            }
            return normal;
        }

    protected abstract MyVector getNormal(MyVector point);
}




