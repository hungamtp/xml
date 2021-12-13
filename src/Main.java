import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String CREATED_FILE = "C:\\Users\\hungnguyenb\\Downloads\\querydsl\\xml\\src\\student.xml";
    public static void main(String[] args){
        updateXmlFile();
    }
    public static List<Student> readListStudents() {
        List<Student> listStudents = new ArrayList<>();
        Student student = null;

        try {
            // đọc file input.xml
            File inputFile = new File("C:\\Users\\hungnguyenb\\Downloads\\querydsl\\xml\\src\\input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // in phần tử gốc ra màn hình
            System.out.println("Phần tử gốc:"
                    + doc.getDocumentElement().getNodeName());

            // đọc tất cả các phần tử có tên thẻ là "student"
            NodeList nodeListStudent = doc.getElementsByTagName("student");

            // duyệt các phần tử student
            for (int i = 0; i < nodeListStudent.getLength(); i++) {
                // tạo đối tượng student
                student = new Student();
                // đọc các thuộc tính của student
                Node nNode = nodeListStudent.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    student.setId(eElement.getAttribute("id"));
                    student.setFirstName(eElement.getElementsByTagName("firstname")
                            .item(0).getTextContent());
                    student.setLastName(eElement.getElementsByTagName("lastname")
                            .item(0).getTextContent());
                    student.setMarks(eElement.getElementsByTagName("marks")
                            .item(0).getTextContent());
                }
                // add đối tượng student vào listStudents
                listStudents.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudents;
    }

    public static void createXmlFile(){
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // tạo phần tử gốc có tên class
            Element rootElement = doc.createElement("class");
            // thêm thuộc tính totalStudents vào thẻ class
            doc.appendChild(rootElement);
            Attr totalStudentAttr = doc.createAttribute("totalStudents");
            totalStudentAttr.setValue("2");
            rootElement.setAttributeNode(totalStudentAttr);

            // tạo phần tử student1
            Element student1 = doc.createElement("student");
            rootElement.appendChild(student1);
            // tạo thuộc tính rollno cho student1
            Attr attr1 = doc.createAttribute("rollno");
            attr1.setValue("1");
            student1.setAttributeNode(attr1);
            // tạo thẻ firstname
            Element firstname = doc.createElement("firstname");
            firstname.appendChild(doc.createTextNode("Vinh"));
            student1.appendChild(firstname);
            // tạo thẻ lastname
            Element lastname = doc.createElement("lastname");
            lastname.appendChild(doc.createTextNode("Phan"));
            student1.appendChild(lastname);

            // tạo phần tử student2
            Element student2 = doc.createElement("student");
            rootElement.appendChild(student2);
            // tạo thuộc tính rollno cho student2
            Attr attr2 = doc.createAttribute("rollno");
            attr2.setValue("2");
            student2.setAttributeNode(attr2);
            // tạo thẻ firstname
            Element firstname2 = doc.createElement("firstname");
            firstname2.appendChild(doc.createTextNode("Hoa"));
            student2.appendChild(firstname2);
            // tạo thẻ lastname
            Element lastname2 = doc.createElement("lastname");
            lastname2.appendChild(doc.createTextNode("Nguyen"));
            student2.appendChild(lastname2);

            // ghi nội dung vào file XML
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(
                    new File(CREATED_FILE));
            transformer.transform(source, result);

            // ghi kết quả ra console để kiểm tra
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateXmlFile(){
        try {
            File inputFile = new File(CREATED_FILE);
            DocumentBuilderFactory docFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            Node classStudent = doc.getFirstChild();
            Node student1 = doc.getElementsByTagName("student").item(0);

            // sửa thuộc tính rollno của student1
            NamedNodeMap attr = student1.getAttributes();
            Node nodeAttr = attr.getNamedItem("rollno");
            nodeAttr.setTextContent("10");

            // sửa firstname của student1
            NodeList list = student1.getChildNodes();

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    if("Vinh".equals(eElement.getTextContent())) {
                        eElement.setTextContent("Test");
                    }
                }
            }

            // xóa student 2
            Node student2 = doc.getElementsByTagName("student").item(1);
            classStudent.removeChild(student2);

            // ghi nội dung được sửa ra console
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            System.out.println("-----------Modified File-----------");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
