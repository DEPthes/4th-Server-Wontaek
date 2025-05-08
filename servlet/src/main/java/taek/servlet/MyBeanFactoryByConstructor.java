package taek.servlet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class MyBeanFactoryByConstructor {

    private final Map<String, Object> beanMap = new HashMap<>();
    private final BeanCreator beanCreator;  // 생성 전략을 주입


    public MyBeanFactoryByConstructor(String configPath, BeanCreator beanCreator) throws Exception {
        this.beanCreator = beanCreator;
        loadBeans(configPath);
    }

    private void loadBeans(String path) throws Exception {
        File file = new File(path);
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        NodeList beans = doc.getElementsByTagName("bean");

        // 1단계: 먼저 빈들을 기본 생성자로 미리 생성 (순환 참조 방지용)
        Map<String, Element> beanElements = new HashMap<>();
        for (int i = 0; i < beans.getLength(); i++) {
            Element bean = (Element) beans.item(i);
            String id = bean.getAttribute("id");
            beanElements.put(id, bean);
        }

        // 2단계: 생성자 주입 방식으로 Bean 생성
        for (String id : beanElements.keySet()) {
            createBean(id, beanElements);
        }
    }

    private Object createBean(String id, Map<String, Element> beanElements) throws Exception {
        if (beanMap.containsKey(id)) {
            return beanMap.get(id); // 이미 생성된 경우
        }

        Element bean = beanElements.get(id);
        String className = bean.getAttribute("class");
        Class<?> clazz = Class.forName(className);

        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            Object refBean = null;

            // 의존하는 Bean을 찾아서 먼저 만들어둔 것 사용
            for (String refId : beanElements.keySet()) {
                if (refId.equals(id)) continue; // 무한 재귀 방지

                Object candidate;
                if (beanMap.containsKey(refId)) {
                    candidate = beanMap.get(refId);
                } else {
                    candidate = createBean(refId, beanElements);
                }

                if (paramTypes[i].isAssignableFrom(candidate.getClass())) {
                    refBean = candidate;
                    break;
                }
            }


            if (refBean == null) {
                throw new RuntimeException("의존성을 찾을 수 없음: " + paramTypes[i].getName());
            }

            params[i] = refBean;
        }

        Object instance = beanCreator.createBean(clazz, params);
        beanMap.put(id, instance);
        return instance;
    }


    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
