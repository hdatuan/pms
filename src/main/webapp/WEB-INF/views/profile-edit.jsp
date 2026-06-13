<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}" />

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <meta name="description" content="">
            <meta name="author" content="">
            <link rel="icon" type="image/png" sizes="16x16" href="${ctx}/plugins/images/favicon.png">
            <title>Pixel Admin</title>
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
            <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
            <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
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
                        <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)"
                            data-toggle="collapse" data-target=".navbar-collapse">
                            <i class="fa fa-bars"></i>
                        </a>
                        <div class="top-left-part">
                            <a class="logo" href="${ctx}/home">
                                <b>
                                    <img src="${ctx}/plugins/images/pixeladmin-logo.png" alt="home" />
                                </b>
                                <span class="hidden-xs">
                                    <img src="${ctx}/plugins/images/pixeladmin-text.png" alt="home" />
                                </span>
                            </a>
                        </div>
                        <ul class="nav navbar-top-links navbar-left m-l-20 hidden-xs">
                            <li>
                                <form role="search" class="app-search hidden-xs">
                                    <input type="text" placeholder="Search..." class="form-control">
                                    <a href="">
                                        <i class="fa fa-search"></i>
                                    </a>
                                </form>
                            </li>
                        </ul>
                        <ul class="nav navbar-top-links navbar-right pull-right">
                            <li>
                                <div class="dropdown">
                                    <a class="profile-pic dropdown-toggle" data-toggle="dropdown" href="#">
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
                    <!-- /.navbar-header -->
                    <!-- /.navbar-top-links -->
                    <!-- /.navbar-static-side -->
                </nav>
                <!-- Left navbar-header -->
                <div class="navbar-default sidebar" role="navigation">
                    <div class="sidebar-nav navbar-collapse slimscrollsidebar">
                        <ul class="nav" id="side-menu">
                            <li style="padding: 10px 0 0;">
                                <a href="${ctx}/home" class="waves-effect"><i class="fa fa-clock-o fa-fw"
                                        aria-hidden="true"></i><span class="hide-menu">Dashboard</span></a>
                            </li>
                            <li>
                                <a href="${ctx}/user" class="waves-effect"><i class="fa fa-user fa-fw"
                                        aria-hidden="true"></i><span class="hide-menu">Thành viên</span></a>
                            </li>
                            <li>
                                <a href="${ctx}/role" class="waves-effect"><i class="fa fa-modx fa-fw"
                                        aria-hidden="true"></i><span class="hide-menu">Quyền</span></a>
                            </li>
                            <li>
                                <a href="${ctx}/groupwork" class="waves-effect"><i class="fa fa-table fa-fw"
                                        aria-hidden="true"></i><span class="hide-menu">Dự án</span></a>
                            </li>
                            <li>
                                <a href="${ctx}/task" class="waves-effect"><i class="fa fa-table fa-fw"
                                        aria-hidden="true"></i><span class="hide-menu">Công việc</span></a>
                            </li>
                            <li>
                                <a href="${ctx}/blank" class="waves-effect"><i class="fa fa-columns fa-fw"
                                        aria-hidden="true"></i><span class="hide-menu">Blank Page</span></a>
                            </li>
                            <li>
                                <a href="${ctx}/403" class="waves-effect"><i class="fa fa-info-circle fa-fw"
                                        aria-hidden="true"></i><span class="hide-menu">Error 403</span></a>
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
                                <h4 class="page-title">Cập nhật công việc</h4>
                            </div>
                        </div>
                        <!-- /.row -->
                        <!-- .row -->
                        <div class="row">
                            <div class="col-md-2 col-12"></div>
                            <div class="col-md-8 col-xs-12">
                                <div class="white-box">
                                    <form action="${ctx}/profile-edit" class="form-horizontal form-material"
                                        method="get">
                                        <div class="form-group">
                                            <label class="col-md-12">Tên dự án</label>
                                            <div class="col-md-12">
                                                <select name="jobId" class="form-control form-control-line"
                                                    onchange="this.form.submit()">
                                                    <option value="">-- Chọn dự án --</option>
                                                    <c:forEach var="item" items="${jobList}">
                                                        <option value="${item.id}" ${param.jobId==item.id ? "selected"
                                                            : "" }>
                                                            ${item.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-12">Tên công việc</label>
                                            <div class="col-md-12">
                                                <select name="taskId" class="form-control form-control-line"
                                                    onchange="this.form.submit()">
                                                    <option value="">-- Chọn công việc --</option>
                                                    <c:forEach var="item" items="${taskList}">
                                                        <option name="taskId" value="${item.id}" ${param.taskId==item.id
                                                            ? "selected" : "" }>
                                                            ${item.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </form>



                                    <form action="${ctx}/profile-edit" class="form-horizontal form-material"
                                        method="post">
                                        <input type="hidden" name="jobId" value="${param.jobId}">
                                        <input type="hidden" name="taskId" value="${param.taskId}">
                                        <div class="form-group">
                                            <label class="col-md-12">Ngày bắt đầu</label>
                                            <div class="col-md-12">
                                                <input type="date" value="${task.start_date}" name="start_date"
                                                    placeholder="YYYY-MM-DD" class="form-control form-control-line">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-12">Ngày kết thúc</label>
                                            <div class="col-md-12">
                                                <input type="date" value="${task.end_date}" name="end_date"
                                                    placeholder="YYYY-MM-DD" class="form-control form-control-line">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-12">Trạng thái</label>
                                            <div class="col-md-12">
                                                <select name="status_id" class="form-control form-control-line">
                                                    <option value=1 ${task.status_id==1 ? "selected" : "" }>Chưa thực
                                                        hiện</option>
                                                    <option value=2 ${task.status_id==2 ? "selected" : "" }>Đang thực
                                                        hiện</option>
                                                    <option value=3 ${task.status_id==3 ? "selected" : "" }>Đã hoàn
                                                        thành</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <c:if test="${not empty param.message}">
                                                <script>
                                                    window.addEventListener('DOMContentLoaded', function() {
                                                        showToast(
                                                            "${param.message == 'success' ? 'Cập nhật thành công!' : param.message == 'fail' ? 'Cập nhật thất bại!' : 'Không có công việc để cập nhật!'}",
                                                            "${param.message == 'success' ? 'success' : 'error'}"
                                                        );
                                                    });
                                                </script>
                                            </c:if>

                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <button type="submit" class="btn btn-success">Lưu lại</button>
                                                <a href="${ctx}/profile" class="btn btn-primary">Quay lại</a>
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