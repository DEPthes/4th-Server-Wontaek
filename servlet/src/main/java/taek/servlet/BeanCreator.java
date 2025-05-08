package taek.servlet;

public interface BeanCreator {

    Object createBean(Class<?> clazz, Object... args) throws Exception;
}
