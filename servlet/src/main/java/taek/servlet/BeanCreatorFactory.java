package taek.servlet;

import java.lang.reflect.Constructor;

public class BeanCreatorFactory implements BeanCreator {

    @Override
    public Object createBean(Class<?> clazz, Object... args) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        return constructor.newInstance(args);
    }
}
