<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>Chi tiết dự án - ${job.name}</title>
    <!-- Bootstrap Core CSS -->
    <link href="${ctx}/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="${ctx}/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <!-- Animation CSS -->
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
                <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)" data-toggle="collapse"
                    data-target=".navbar-collapse">
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
                            <a href=""><i class="fa fa-search"></i></a>
                        </form>
                    </li>
                </ul>
                <ul class="nav navbar-top-links navbar-right pull-right">
                    <li>
                        <div class="dropdown">
                            <a class="profile-pic dropdown-toggle" data-toggle="dropdown" href="#">
                                <img src="${ctx}/plugins/images/users/varun.jpg" alt="user-img" width="36"
                                    class="img-circle" />
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
        </nav>
        <!-- Left navbar-header -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse slimscrollsidebar">
                <ul class="nav" id="side-menu">
                    <li style="padding: 10px 0 0;">
                        <a href="${ctx}/home" class="waves-effect"><i class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span
                                class="hide-menu">Dashboard</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/user" class="waves-effect"><i class="fa fa-user fa-fw" aria-hidden="true"></i><span
                                class="hide-menu">Thành viên</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/role" class="waves-effect"><i class="fa fa-modx fa-fw" aria-hidden="true"></i><span
                                class="hide-menu">Quyền</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/groupwork" class="waves-effect"><i class="fa fa-table fa-fw"
                                aria-hidden="true"></i><span class="hide-menu">Dự án</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/task" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span
                                class="hide-menu">Công việc</span></a>
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
                <!-- Page Title -->
                <div class="row bg-title">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                        <h4 class="page-title">Chi tiết dự án: <strong>${job.name}</strong></h4>
                    </div>
                    <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12 text-right">
                        <a href="${ctx}/groupwork" class="btn btn-sm btn-primary">
                            <i class="fa fa-arrow-left"></i> Quay lại danh sách
                        </a>
                    </div>
                </div>

                <!-- Thông tin dự án -->
                <div class="row m-t-15">
                    <div class="col-sm-12">
                        <div class="white-box">
                            <h3 class="box-title">Thông tin dự án</h3>
                            <p><strong>Tên dự án:</strong> ${job.name}</p>
                            <p><strong>Ngày bắt đầu:</strong> ${job.start_date}</p>
                            <p><strong>Ngày kết thúc:</strong> ${job.end_date}</p>
                            <p><strong>Tổng số công việc:</strong> ${total}</p>
                        </div>
                    </div>
                </div>

                <!-- BEGIN THỐNG KÊ TIẾN ĐỘ -->
                <div class="row">
                    <!-- Chưa bắt đầu -->
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <i class="fa fa-circle-o fa-2x text-danger"></i>
                                    <h5 class="text-muted vb">CHƯA BẮT ĐẦU</h5>
                                    <small>${notStarted} / ${total} công việc</small>
                                </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <h3 class="counter text-right m-t-15 text-danger">${pctNotStarted}%</h3>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-danger" role="progressbar"
                                            aria-valuenow="${pctNotStarted}" aria-valuemin="0" aria-valuemax="100"
                                            style="width: ${pctNotStarted}%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col -->

                    <!-- Đang thực hiện -->
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <i class="fa fa-spinner fa-2x text-megna"></i>
                                    <h5 class="text-muted vb">ĐANG THỰC HIỆN</h5>
                                    <small>${inProgress} / ${total} công việc</small>
                                </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <h3 class="counter text-right m-t-15 text-megna">${pctInProgress}%</h3>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-megna" role="progressbar"
                                            aria-valuenow="${pctInProgress}" aria-valuemin="0" aria-valuemax="100"
                                            style="width: ${pctInProgress}%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col -->

                    <!-- Hoàn thành -->
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <i class="fa fa-check-circle fa-2x text-success"></i>
                                    <h5 class="text-muted vb">HOÀN THÀNH</h5>
                                    <small>${done} / ${total} công việc</small>
                                </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <h3 class="counter text-right m-t-15 text-primary">${pctDone}%</h3>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-primary" role="progressbar"
                                            aria-valuenow="${pctDone}" aria-valuemin="0" aria-valuemax="100"
                                            style="width: ${pctDone}%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- END THỐNG KÊ TIẾN ĐỘ -->

                <!-- BEGIN DANH SÁCH CÔNG VIỆC THEO NHÂN VIÊN -->
                <c:choose>
                    <c:when test="${total == 0}">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="white-box text-center">
                                    <p class="text-muted">Chưa có công việc nào trong dự án này.</p>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="entry" items="${tasksByUser}">
                            <div class="row">
                                <div class="col-xs-12">
                                    <h4 class="group-title" style="padding: 10px 15px; background:#f5f5f5; border-left: 4px solid #00b0ff; margin: 10px 0 5px;">
                                        <i class="fa fa-user"></i> ${entry.key}
                                    </h4>
                                </div>
                                <!-- Chưa thực hiện -->
                                <div class="col-md-4">
                                    <div class="white-box">
                                        <h3 class="box-title text-danger"><i class="fa fa-circle-o"></i> Chưa thực hiện</h3>
                                        <div class="message-center">
                                            <c:set var="hasTask" value="false" />
                                            <c:forEach var="t" items="${entry.value}">
                                                <c:if test="${t.status_id == 1}">
                                                    <c:set var="hasTask" value="true" />
                                                    <div style="padding: 8px 0; border-bottom: 1px solid #eee;">
                                                        <div class="mail-contnet">
                                                            <h5>${t.name}</h5>
                                                            <span class="mail-desc text-muted">
                                                                ${t.start_date} → ${t.end_date}
                                                            </span>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${!hasTask}">
                                                <p class="text-muted" style="padding: 8px 0;">Không có</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                                <!-- Đang thực hiện -->
                                <div class="col-md-4">
                                    <div class="white-box">
                                        <h3 class="box-title text-megna"><i class="fa fa-spinner"></i> Đang thực hiện</h3>
                                        <div class="message-center">
                                            <c:set var="hasTask" value="false" />
                                            <c:forEach var="t" items="${entry.value}">
                                                <c:if test="${t.status_id == 2}">
                                                    <c:set var="hasTask" value="true" />
                                                    <div style="padding: 8px 0; border-bottom: 1px solid #eee;">
                                                        <div class="mail-contnet">
                                                            <h5>${t.name}</h5>
                                                            <span class="mail-desc text-muted">
                                                                ${t.start_date} → ${t.end_date}
                                                            </span>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${!hasTask}">
                                                <p class="text-muted" style="padding: 8px 0;">Không có</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                                <!-- Đã hoàn thành -->
                                <div class="col-md-4">
                                    <div class="white-box">
                                        <h3 class="box-title text-success"><i class="fa fa-check-circle"></i> Đã hoàn thành</h3>
                                        <div class="message-center">
                                            <c:set var="hasTask" value="false" />
                                            <c:forEach var="t" items="${entry.value}">
                                                <c:if test="${t.status_id == 3}">
                                                    <c:set var="hasTask" value="true" />
                                                    <div style="padding: 8px 0; border-bottom: 1px solid #eee;">
                                                        <div class="mail-contnet">
                                                            <h5>${t.name}</h5>
                                                            <span class="mail-desc text-muted">
                                                                ${t.start_date} → ${t.end_date}
                                                            </span>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${!hasTask}">
                                                <p class="text-muted" style="padding: 8px 0;">Không có</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <!-- END DANH SÁCH CÔNG VIỆC THEO NHÂN VIÊN -->

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
