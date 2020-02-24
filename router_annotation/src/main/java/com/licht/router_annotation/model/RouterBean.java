package com.licht.router_annotation.model;

import javax.lang.model.element.Element;

public class RouterBean {
    public enum Type{
        ACTIVITY
    }
    private Type type;
    private Element element;
    private Class<?> aClass;
    private String group;
    private String path;

    private RouterBean(Builder builder) {
        this.element = builder.element;
        this.aClass = builder.aClass;
        this.group = builder.group;
        this.path = builder.path;
        this.type = builder.type;
    }
    public final static class Builder{
        private Type type;
        private Element element;
        private Class<?> aClass;
        private String group;
        private String path;

        public Builder setType(Type type) {
            this.type = type;
            return  this;
        }

        public Builder setElement(Element element) {
            this.element = element;
            return  this;
        }

        public Builder setaClass(Class<?> aClass) {
            this.aClass = aClass;
            return  this;
        }

        public Builder setGroup(String group) {
            this.group = group;
            return  this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return  this;
        }

        public  RouterBean build(){
            if (path == null || path.length() == 0){
                throw  new IllegalArgumentException("RouterBean : path is null");
            }
            return new RouterBean(this);
        }
    }



    public static RouterBean create(Type type, Class<?> aClass,  String group, String path) {
     return new RouterBean(type,aClass,group,path);
    }


    private RouterBean(Type type,  Class<?> aClass, String group, String path) {
        this.type = type;
        this.aClass = aClass;
        this.group = group;
        this.path = path;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "RouterBean{" +
                "type=" + type +
                ", element=" + element +
                ", aClass=" + aClass +
                ", group='" + group + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public Type getType() {
        return type;
    }

    public Element getElement() {
        return element;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public String getGroup() {
        return group;
    }

    public String getPath() {
        return path;
    }


}
