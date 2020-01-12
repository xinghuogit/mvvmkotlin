package com.spark.mvvmjava.bean;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/*************************************************************************************************
 * 日期：2020/1/7 10:35
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
/*单列索引          @Entity(indices = {@Index(value = "name")})
单列索引唯一性    @Entity(indices = {@Index(value = "name", unique = true)})
组合索引          @Entity(indices ={@Index(value = {"name","age"})})
组合索引唯一性    @Entity(indices ={@Index(value = {"name","age"},unique = true)})
当然可以混起来用 @Entity(indices ={@Index(value = "name"),@Index(value = {"name","age"},unique = true)})*/
//后面可以修改表明 @Entity(tableName = "UserInfo") //@Entity(primaryKeys = {"uid","name"})
@Entity
public class User {

   /* //@Entity(primaryKeys = {"uid","name"})  //name字段要用@NonNull标注@NonNull
      复合主键：多字段主键则构成主键的多个字段的组合不得有重复
     （假如我们用name做主键，如果我们有2个name相同的数据一起插入，
     数据就会被覆盖掉。但是现实中真的有同名的人，是2条数据，
     这时候我们就要用name和出生日期作为复合主键也就是多个主键，主键都一致才会覆盖数据）*/

    /* @PrimaryKey(autoGenerate = true)标识uid为主键，且设置为自增长。
     设置为主键的字段不得为空也不允许有重复值。
     也可以直接默认@PrimaryKey，需要用户自己设置不重复的数值*/
    @PrimaryKey(autoGenerate = true)
    private long uuid;

    /*设置@ColumnInfo(name = "mName") 在数据库表里的key就是mName*/
    @ColumnInfo(name = "mName")
    private String name;

    //    @ColumnInfo
    private String sex;

    private String phone;

    /**
     * @Ignore 忽略掉该字段，不在数据库表中建该字段
     */
    @Ignore
    private int age;

    /**
     * @Embedded 实体类中引用其他实体类。这样的话Dog里属性也成为了表person的字段。
     * 那么如果有两条狗呢？可以这么设置prefix
     * @Embedded(prefix = "one")
     * private Dog dog;
     * @Embedded(prefix = "two")
     * private Dog dog;
     */
    @Embedded
    private Dog dog;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, String sex, int age) {
//        this.uuid = uuid;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public User(String name, String sex, int age, Dog dog) {
//        this.uuid = uuid;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.dog = dog;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
