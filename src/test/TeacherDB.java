package test;

import java.util.ArrayList;

import com.api.model.teacher;
import com.dbop.TeacherDBOP;

public class TeacherDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TeacherDBOP db = new TeacherDBOP();
		teacher t = new teacher();
		ArrayList<teacher> list = new ArrayList<teacher>();
		t.setId("0021");
		t.setName("罗老师");
		db.insert(t);
		list = db.queryAll();
		for(teacher a : list){
			System.out.println(a.getId()+"  "+a.getName());
		}
	}

}
