package planit;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Entity
public class Calendar {
	@Id long id;
    String name;

    public Calendar(String name) {
        this.name = name;
    }
}
