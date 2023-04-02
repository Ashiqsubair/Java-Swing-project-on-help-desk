Help desk.
it's a simple java swing project which connects with the Oracle database.

User Interface
>User can enter the issue and submit the request.
>A ticket will be generated. Keep note of the ticket number displayed on the popup.
>Check the status of the ticket with the ticket number.

Agent Interface
>Login with the emailID and password.
>New tickets will be listed in the dashboard. select any ticket and add it to your name.
>Select the tickets which are on your name and type the secret password which are provided to the users to resolve the ticket.
>Resolved tickets will be seen on the same dashboard screen.

#Pre-requisites
>java swing must be installed on to your eclipse IDE.
>Eexecution enivironment of the project should be JavaSE-1.7(jdk-19).
>Add external jar files to your project.
> 1. ojdbc8 jar file. link - https://jar-download.com/?search_box=ojdbc8+jar
> 2. rs2xml jar file. link - https://sourceforge.net/projects/finalangelsanddemons/files/rs2xml.jar/download

#SQL tables which need to be created before execution of the project.
>1. create table usertable (userid varchar2(40) primary key,username varchar2(40), useremail varchar2(40));
>2. create table catagorytable (catagoryid varchar2(10) primary key,catagoryname varchar2(20));
>3. create table tickettable (ticketid varchar2(40) primary key,userid varchar2(40) references usertable(userid),description varchar2(600), catagoryid references catagorytable(catagoryid),status varchar2(20), key varchar2(30));
