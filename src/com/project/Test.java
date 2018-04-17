package com.project;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args){
        Filter f1=new Filter();
        f1.setId(10);//查询id为10的用户

        Filter f2=new Filter();
        f2.setUserName("lucy");//模糊查询用户名为lucy的用户

        Filter f3=new Filter();
        //查询邮箱为其中任意一个的用户
        f3.setEmail("liu@sina.com,zh@163.com,666666@qq.com");

        String sql1=query(f1);
        String sql2=query(f2);
        String sql3=query(f3);

        System.out.println(sql1);
        System.out.println(sql2);
        System.out.println(sql3);

    }

    private static String query(Filter f) {

        StringBuilder sb=new StringBuilder();
        //1、获取到类class
        Class c=f.getClass();
        //2、获取到table的名字
        boolean isExist=c.isAnnotationPresent(Table.class);
        if (!isExist){
            return null;
        }
        Table t=(Table)c.getAnnotation(Table.class);
        String tableName=t.value();
        sb.append("select* from ").append(tableName).append(" where 1=1");
        //获得所有字段
        Field[] fArray=c.getDeclaredFields();
        //3、遍历所有的字段
        for (Field field:fArray) {
        //4、处理每个字段对应的sql
        //4.1拿到字段名
            boolean exists=field.isAnnotationPresent(Column.class);
            if (!exists){
                continue;
            }
            Column column=field.getAnnotation(Column.class);
            //获得注解的内容
            String columnName=column.value();

           //4.2拿到字段的值
            String filedName=field.getName();//获得字段的名字
            //拿到每个字段的get方法
            //substring(int beginIndex)
            //返回从起始位置（beginIndex）至字符串末尾的字符串
            //substring(int beginIndex, int endIndex)
            //返回从起始位置（beginIndex）到目标位置（endIndex）之间的字符串，但不包含目标位置（endIndex）的字符
            String getMethodName="get"+filedName.substring(0,1).toUpperCase()+filedName.substring(1);
            //通过反射用方法名拿到每个字段的get方法
            Object fieldValue=null;
            try {
                Method getMethod=c.getMethod(getMethodName);
                //调用get方法获得对应字段的值
                //f对象中带有参数args的getMethod方法，返回值是Object
                fieldValue=getMethod.invoke(f,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //4.3拼装sql
            if(fieldValue==null||(fieldValue instanceof Integer && (Integer)fieldValue==0)){
                continue;
            }
            sb.append(" and ").append(filedName).append("=");
            if(fieldValue instanceof String){
               sb.append("'"+fieldValue+"'");
            }else{
                sb.append(fieldValue);
            }

        }
        return sb.toString();
    }
}
