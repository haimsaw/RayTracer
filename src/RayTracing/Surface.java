package RayTracing;
import java.util.List;

public abstract class Surface {

    Material material;

    public Surface(Material material) {
        this.material = material;
    };

    public abstract List<MyVector> get_intersections();
    public abstract MyVector get_normal(MyVector point);

}

