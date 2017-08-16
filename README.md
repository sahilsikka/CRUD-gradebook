# CRUD-gradebook
1)	Unzip the zip file.
2)	Open netbeans and import both CRUD-GradeBook-ssikka and CRUD-GradeBook-ssikka_client projects
3)	Clean and build both the projects
4)	Run the server project CRUD-GradeBook-ssikka and then run the client project assignment1_client (GradeBook_UI.java contains the main function).
Here the student id is generated randomly.
Test Cases
Create – Select Instructor and tap create radio button.
1)    201, CREATED -  add all the details and then tap submit .
2)    409, CONFLICT – This occurs when student name and workitem already exists with another id.
3)    400, BAD REQUEST – Leave student name empty
4)     500, Internal server error- occurs when there is a runtime exception
Read – Select read radio button under student or instructor
1)	200, OK – enter a student Id and workitem and click submit
2)	410 Gone- occurs when no information has been created and user is reading an id and workitem.
3)	404, NOT Found- occurs when a student id does not exist
Update- Select update radio button under instructor
1)	200 OK- enter a student id that exists and all other details. Finally click on submit
2)	409, Conflict- when the entered student id and workitem does not exist.
3)	400 bad request- occurs when student name and workitem is not entered
Appeal- Select appeal radio button under student
1)	200 OK- enter a student id that exists and all other details. Finally click on submit
2)	409, Conflict - when the entered student id does not exist.
3)	400 bad request- occurs when student name and work student is not entered
Delete-	Select delete radio button under instructor
1)	404 not found – when entered id is not found
2)	409 GONE- when there is not record in the system and user is reading a studentid and workitem
3)	204 no content- when the entered id is successfully deleted
