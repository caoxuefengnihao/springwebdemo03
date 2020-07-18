package cn.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("stu")
@Scope(value="prototype")
public class stu {
    String name;

    public stu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public stu(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "stu{" +
                "name='" + name + '\'' +
                '}';
    }
}
