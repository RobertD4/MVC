package ro.teamnet.zth.web;

import oracle.jdbc.proxy.annotation.Methods;
import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Robert.Dumitrescu on 7/20/2017.
 */
public class MyDispatcherServlet extends HttpServlet {

    Map<String, MethodAttributes> allowedMethods;
    public void init() {
        try {
            ArrayList<Class> classes = (ArrayList<Class>) AnnotationScanUtils.getClasses("ro.teamnet.zth.controller");
            for (Class cls : classes) {
                String url = null;
                for (Method met : cls.getDeclaredMethods()) {
                    MyController myController = (MyController) cls.getDeclaredAnnotation(MyController.class);

                    MyRequestMethod myRequestMethod =  met.getDeclaredAnnotation(MyRequestMethod.class);
                    url = "192.168.99.100:1521/app/mvc/"
                            + myController.urlPath() + myRequestMethod.urlPath() + myRequestMethod.methodType();
                    MethodAttributes ma = new MethodAttributes();
                    ma.setControllerClass(myController.urlPath());
                    ma.setMethodName(myRequestMethod.urlPath());
                    ma.setMethodType(myRequestMethod.methodType());
                    allowedMethods.put(url,ma);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dispatch(request,response,"POST");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dispatch(request,response,"GET");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void dispatchReply(HttpServletRequest request, HttpServletResponse response, String reqType) throws ServletException, IOException{
        try {
            Object resultToDisplay = dispatch(request, response, reqType);
            reply((String) resultToDisplay,response);
        }catch(Exception e){
            sendExceptionError();
        }

    }

    private Object dispatch(HttpServletRequest request, HttpServletResponse response, String reqType) throws NoSuchMethodException, ClassNotFoundException {
        String key =request.getRequestURI() + reqType;
        MethodAttributes m = allowedMethods.get(key);
         String cls = m.getControllerClass();
            Class clas = Class.forName(cls);
            String result = null;
        try {
           // Method method = cls.getClass().getMethod(m.getMethodName());
            Method method = clas.getMethod(m.getMethodName());
            result = (String) method.invoke(clas.newInstance());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        return  result;
    }

    private void reply(String resultToDisplay, HttpServletResponse response) throws IOException {
        response.getWriter().write(resultToDisplay);
    }

    private void sendExceptionError() {

    }

}
