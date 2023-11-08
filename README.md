# DistributedSystems
--------------student management----------------------<br/>
We can add student details like university roll number, first name, last name, contact details, course, studying year.<br/>
Get list of students details.<br/>
Retrieve student details by university roll number.<br/>
<br/>
-------------result management------------------------<br/>
We can add result details like student roll number, subject marks and cgpa.<br/>
Retrieve results of a student by university roll number.<br/>
Get result of students who are above the given cgpa.<br/>
<br/>
-------------placement management---------------------<br/>
We can add company details like company name, package offered, minimum cgpa required for eligibility.<br/>
Retrieve company details with eligible student list with id.<br/>

-------------Connection between the systems------------<br/>
Result management get data from student management using RestTemplate library.<br/>
Placement management get data from result management using WebClient.<br/>



