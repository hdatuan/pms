<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Chỉnh sửa tác vụ - PMS">
    <link rel="icon" type="image/png" sizes="16x16" href="${ctx}/plugins/images/favicon.png">
    <title>Chỉnh sửa Tác Vụ | Pixel Admin</title>
    <!-- Bootstrap Core CSS -->
    <link href="${ctx}/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="${ctx}/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <!-- animation CSS -->
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet">
    <!-- color CSS -->
    <link href="${ctx}/css/colors/blue-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/css/custom.css">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>

<body>
    <!-- Preloader -->
    <div class="preloader">
        <div class="cssload-speeding-wheel"></div>
    </div>
    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top m-b-0">
            <div class="navbar-header">
                <a class="navbar-toggle hidden-sm hidden-md hidden-lg" href="javascript:void(0)"
                    data-toggle="collapse" data-target=".navbar-collapse">
                    <i class="fa fa-bars"></i>
                </a>
                <div class="top-left-part">
                    <a class="logo" href="${ctx}/home">
                        <b><img src="${ctx}/plugins/images/pixeladmin-logo.png" alt="home" /></b>
                        <span class="hidden-xs">
                            <img src="${ctx}/plugins/images/pixeladmin-text.png" alt="home" />
                        </span>
                    </a>
                </div>
                <ul class="nav navbar-top-links navbar-left m-l-20 hidden-xs">
                    <li>
                        <form role="search" class="app-search hidden-xs">
                            <input type="text" placeholder="Search..." class="form-control">
                            <a href=""><i class="fa fa-search"></i></a>
                        </form>
                    </li>
                </ul>
                <ul class="nav navbar-top-links navbar-right pull-right">
                    <li>
                        <div class="dropdown">
                            <a class="profile-pic dropdown-toggle" data-toggle="dropdown" href="#">
                                <img src="${ctx}/plugins/images/users/varun.jpg" alt="user-img" width="36" class="img-circle" />
                                <b class="hidden-xs">${sessionScope.user.fullname}</b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="${ctx}/profile">Thông tin cá nhân</a></li>
                                <li class="divider"></li>
                                <li><a href="${ctx}/logout">Đăng xuất</a></li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
        <!-- Left navbar-header -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse slimscrollsidebar">
                <ul class="nav" id="side-menu">
                    <li style="padding: 10px 0 0;">
                        <a href="${ctx}/home" class="waves-effect"><i class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span class="hide-menu">Dashboard</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/user" class="waves-effect"><i class="fa fa-user fa-fw" aria-hidden="true"></i><span class="hide-menu">Thành viên</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/role" class="waves-effect"><i class="fa fa-modx fa-fw" aria-hidden="true"></i><span class="hide-menu">Quyền</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/groupwork" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Dự án</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/task" class="waves-effect"><i class="fa fa-tasks fa-fw" aria-hidden="true"></i><span class="hide-menu">Công việc</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/blank" class="waves-effect"><i class="fa fa-columns fa-fw" aria-hidden="true"></i><span class="hide-menu">Blank Page</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/403" class="waves-effect"><i class="fa fa-info-circle fa-fw" aria-hidden="true"></i><span class="hide-menu">Error 403</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- Left navbar-header end -->
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row bg-title">
                    <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                        <h4 class="page-title">Chỉnh sửa tác vụ</h4>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-md-2 col-12"></div>
                    <div class="col-md-8 col-xs-12">
                        <div class="white-box">
                            <form class="form-horizontal form-material" action="${ctx}/task-edit" method="post" id="form-task-edit">
                                <!-- Hidden ID -->
                                <input type="hidden" name="id" value="${editTask.id}">
                                <!-- Tên tác vụ -->
                                <div class="form-group">
                                    <label class="col-md-12">Tên tác vụ <span class="text-danger">*</span></label>
                                    <div class="col-md-12">
                                        <input type="text" name="name" id="task-edit-name"
                                            placeholder="Nhập tên tác vụ"
                                            value="${editTask.name}"
                                            class="form-control form-control-line" required>
                                    </div>
                                </div>
                                <!-- Dự án -->
                                <div class="form-group">
                                    <label class="col-md-12">Dự án <span class="text-danger">*</span></label>
                                    <div class="col-md-12">
                                        <select name="job_id" id="task-edit-job" class="form-control form-control-line" required>
                                            <option value="">-- Chọn dự án --</option>
                                            <c:forEach items="${jobs}" var="job">
                                                <option value="${job.id}"
                                                    <c:if test="${job.id == editTask.job_id}">selected</c:if>>
                                                    ${job.name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <!-- Người thực hiện -->
                                <div class="form-group">
                                    <label class="col-md-12">Người thực hiện <span class="text-danger">*</span></label>
                                    <div class="col-md-12">
                                        <select name="user_id" id="task-edit-user" class="form-control form-control-line" required>
                                            <option value="">-- Chọn người thực hiện --</option>
                                            <c:forEach items="${users}" var="user">
                                                <option value="${user.id}"
                                                    <c:if test="${user.id == editTask.user_id}">selected</c:if>>
                                                    ${user.fullname}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <!-- Trạng thái -->
                                <div class="form-group">
                                    <label class="col-md-12">Trạng thái <span class="text-danger">*</span></label>
                                    <div class="col-md-12">
                                        <select name="status_id" id="task-edit-status" class="form-control form-control-line" required>
                                            <option value="">-- Chọn trạng thái --</option>
                                            <option value="1" <c:if test="${editTask.status_id == 1}">selected</c:if>>Chưa thực hiện</option>
                                            <option value="2" <c:if test="${editTask.status_id == 2}">selected</c:if>>Đang thực hiện</option>
                                            <option value="3" <c:if test="${editTask.status_id == 3}">selected</c:if>>Hoàn thành</option>
                                        </select>
                                    </div>
                                </div>
                                <!-- Ngày bắt đầu -->
                                <div class="form-group">
                                    <label class="col-md-12">Ngày bắt đầu</label>
                                    <div class="col-md-12">
                                        <input type="date" name="start_date" id="task-edit-start-date"
                                            value="${editTask.start_date}"
                                            class="form-control form-control-line">
                                    </div>
                                </div>
                                <!-- Ngày kết thúc -->
                                <div class="form-group">
                                    <label class="col-md-12">Ngày kết thúc</label>
                                    <div class="col-md-12">
                                        <input type="date" name="end_date" id="task-edit-end-date"
                                            value="${editTask.end_date}"
                                            class="form-control form-control-line">
                                    </div>
                                </div>
                                <!-- Toast khi có lỗi validation (ở lại trang) -->
                                <c:if test="${isDone}">
                                    <script>
                                        window.addEventListener('DOMContentLoaded', function() {
                                            showToast("${message}", "${isSuccess ? 'success' : 'error'}");
                                        });
                                    </script>
                                </c:if>
                                <!-- Nút hành động -->
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <button type="submit" class="btn btn-success" id="btn-submit-task-edit">
                                            <i class="fa fa-save"></i> Cập nhật
                                        </button>
                                        <a href="${ctx}/task" class="btn btn-primary" id="btn-back-task-edit">
                                            <i class="fa fa-arrow-left"></i> Quay lại
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col-md-2 col-12"></div>
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
            <footer class="footer text-center">
                <script>document.write(new Date().getFullYear())</script> &copy; hdatuan
            </footer>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    <!-- jQuery -->
    <script src="${ctx}/plugins/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Common Helper Functions -->
    <script src="${ctx}/js/common.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="${ctx}/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Menu Plugin JavaScript -->
    <script src="${ctx}/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
    <!--slimscroll JavaScript -->
    <script src="${ctx}/js/jquery.slimscroll.js"></script>
    <!--Wave Effects -->
    <script src="${ctx}/js/waves.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="${ctx}/js/custom.min.js"></script>
</body>

</html>
