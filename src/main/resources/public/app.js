app=angular.module("newsportal",[]);

app.controller("appcontrol",function($scope,$http,$timeout){

       $scope.view_departments=true;
       $scope.view_department_users=null;
       $scope.view_department_news=null;

       $scope.department_name="";
       $scope.department_desc="";

       $scope.user_name="";
       $scope.user_position="";
       $scope.user_role="";
       $scope.user_department="";

       $scope.btn_dept_label="Add Department";
       $scope.btn_dept_class="bg-success";

        $scope.newDepartment=function(){

          let departments = $scope.departments.map(dpt=>dpt.name);

          if(departments.includes($scope.department_name)){
            $scope.btn_dept_label="That department already exists!";
            $scope.btn_dept_class="bg-danger";
            //alert("That department already exists!");
            $timeout(function(){$scope.btn_dept_label="Add Department"; $scope.btn_dept_class="bg-success";},3000);
          }else if($scope.department_name.length==0 || $scope.department_desc.length==0 ){
            alert("Please provide all inputs!");
          }
          else{

          department = {"name": $scope.department_name, "description":  $scope.department_desc};
          console.log(department);

          $http.post("/Departments/new",department)
          .then(function(response){$scope.departments=response.data; console.log($scope.departments); });

          $scope.department_name="";
          $scope.department_desc="";
          }
        }

        $scope.newUser=function(){

          let users = $scope.users.map(u=>u.name);

          if(users.includes($scope.user_name)){alert("That user already exists");}
          else if($scope.user_name.length==0 || $scope.user_position.length==0 ||
                  $scope.user_role.length==0 ||$scope.user_dept.name.length==0 ){
                    alert("Please provide all inputs!");
          }
          else{

          user = {"name": $scope.user_name, "position":$scope.user_position, "role":  $scope.user_role, "departmentId":$scope.user_dept.id};
          console.log(user);

          $http.post("/Users/new",user)
          .then(function(response){$scope.users=response.data; console.log($scope.users); });

          $scope.user_name="";
          $scope.user_position="";
          $scope.user_role="";
          }
        }

        $scope.newGeneralNews=function(){

          news = {"userid": $scope.user.id, "type":"general", "content":  $scope.news_content};
          console.log(news);

          $http.post("/News/new",news)
          .then(function(response){$scope.general_news=response.data; console.log($scope.general_news); });

          $scope.user_name="";
          $scope.news_content="";

        }

        $scope.newDepartmentNews=function(){

          news = {"userid": $scope.user.id, "type":"department", "content":  $scope.news_content};
          console.log(news);

          $http.post("/DepartmentNews/new",news)
          .then(function(response){$scope.department_news=response.data; console.log($scope.department_news); });

          $scope.user_name="";
          $scope.news_content="";

        }

        $scope.viewDepartments=function(){
          $scope.view_departments=true;
          $scope.view_department_users=null;
          $scope.view_department_news=null;
        }

        $scope.viewDepartmentUsers=function(id){

          $scope.view_departments=null;
          $scope.view_department_users=true;

          $http.get("/departments/"+id+"/users")
         .then(function(response){
             $scope.department_users_list =response.data;
          });
        }

        $scope.viewDepartmentNews=function(id){

          $scope.view_departments=null;
          $scope.view_department_news=true;

          $http.get("/departments/"+id+"/news")
         .then(function(response){
             $scope.department_news_list =response.data;
          });
        }


         $http.get("/users")
         .then(function(response){
             $scope.users =response.data;
             //console.log($scope.users.length);
             //console.log($scope.users);
             });

         $http.get("/departments")
         .then(function(response){
             $scope.departments =response.data;
             //console.log($scope.users.length);
             //console.log($scope.users);
             });

         $http.get("/news")
         .then(function(response){
             $scope.news =response.data;
             //console.log($scope.users.length);
             //console.log($scope.users);
             });

         $http.get("/news/general")
         .then(function(response){
             $scope.general_news =response.data;
             //console.log($scope.users.length);
             //console.log($scope.users);
             });

         $http.get("/news/department")
         .then(function(response){
             $scope.department_news =response.data;
             //console.log($scope.users.length);
             //console.log($scope.users);
             });

     });