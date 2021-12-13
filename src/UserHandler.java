import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class UserHandler extends DefaultHandler {
    private String content;
    private Student student;
    private List<Student> listStudents = new ArrayList<>();;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // tạo đối tượng Student khi bắt đầu thẻ "student"
        if ("student".equalsIgnoreCase(qName)) {
            student = new Student();
            student.setId(attributes.getValue("id"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        switch (qName) {
            case "student":
                // add đối tượng Student vào list khi gặp thẻ đóng "student"
                listStudents.add(student);
                break;
            case "firstname":
                student.setFirstName(content);
                break;
            case "lastname":
                student.setLastName(content);
                break;
            case "marks":
                student.setMarks(content);
                break;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        // đọc nội dung của thẻ hiện tại
        content = String.copyValueOf(ch, start, length).trim();
    }

    public List<Student> getListStudents() {
        return listStudents;
    }

    public void setListStudents(List<Student> listStudents) {
        this.listStudents = listStudents;
    }
}
