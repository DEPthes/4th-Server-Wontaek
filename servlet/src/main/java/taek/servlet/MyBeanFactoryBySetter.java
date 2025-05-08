package taek.servlet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MyBeanFactoryBySetter {
    private final Map<String, Object> beanMap = new HashMap<>();

    public MyBeanFactoryBySetter(String configPath) throws Exception {
        loadBeans(configPath);
    }

    private void loadBeans(String path) throws Exception {
        File file = new File(path);
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        NodeList beans = doc.getElementsByTagName("bean");

        // 1단계: 빈 생성
        for (int i = 0; i < beans.getLength(); i++) {
            Element bean = (Element) beans.item(i);
            String id = bean.getAttribute("id");
            String className = bean.getAttribute("class");

            Class<?> clazz = Class.forName(className);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            beanMap.put(id, instance);
        }

        // 2단계: 의존성 주입
        for (int i = 0; i < beans.getLength(); i++) {
            Element bean = (Element) beans.item(i);
            String id = bean.getAttribute("id");
            Object targetObj = beanMap.get(id);

            NodeList properties = bean.getElementsByTagName("property");

            for (int j = 0; j < properties.getLength(); j++) {
                Element prop = (Element) properties.item(j);
                String name = prop.getAttribute("name");
                String ref = prop.getAttribute("ref");

                Object refObj = beanMap.get(ref);

                String setterName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method setter = targetObj.getClass().getMethod(setterName, refObj.getClass());
                setter.invoke(targetObj, refObj);
            }
        }
    }

    public Object getBean(String id) {
        return beanMap.get(id);
    }

}
