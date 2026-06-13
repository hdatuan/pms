<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}" />

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <meta name="description" content="Quản lý tác vụ - PMS">
            <meta name="author" content="">
            <link rel="icon" type="image/png" sizes="16x16" href="${ctx}/plugins/images/favicon.png">
            <title>Quản lý Tác vụ | Pixel Admin</title>
            <!-- Bootstrap Core CSS -->
            <link href="${ctx}/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
            <!-- Menu CSS -->
            <link href="${ctx}/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
            <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
            <!-- animation CSS -->
            <link href="${ctx}/css/animate.css" rel="stylesheet">
            <!-- Custom CSS -->
            <link href="${ctx}/css/style.css" rel="stylesheet">
            <!-- color CSS -->
            <link href="${ctx}/css/colors/blue-dark.css" id="theme" rel="stylesheet">
            <link rel="stylesheet" href="${ctx}/css/custom.css">
            <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
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
                                <a href="${ctx}/task" class="waves-effect"><i class="fa fa-tasks fa-fw"
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
                                <h4 class="page-title">Danh sách tác vụ</h4>
                            </div>
                            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                                <c:if test="${canManage}">
                                    <a href="${ctx}/task-add" class="btn btn-sm btn-success" id="btn-task-add">
                                        <i class="fa fa-plus"></i> Thêm mới
                                    </a>
                                </c:if>
                            </div>
                        </div>
                        <!-- /row -->
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="white-box">
                                    <div class="table-responsive">
                                        <table class="table table-hover" id="taskTable">
                                            <thead>
                                                <tr>
                                                    <th>STT</th>
                                                    <th>Tên Tác Vụ</th>
                                                    <th>Dự Án</th>
                                                    <th>Người Thực Hiện</th>
                                                    <th>Ngày Bắt Đầu</th>
                                                    <th>Ngày Kết Thúc</th>
                                                    <th>Trạng Thái</th>
                                                    <c:if test="${canManage}">
                                                        <th>Hành động</th>
                                                    </c:if>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${tasks}" var="item" varStatus="loop">
                                                    <tr>
                                                        <td>${loop.index + 1}</td>
                                                        <td>${item.name}</td>
                                                        <td>${item.job_name}</td>
                                                        <td>${item.user_name}</td>
                                                        <td>${item.start_date}</td>
                                                        <td>${item.end_date}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${item.status_name == 'Hoàn thành'}">
                                                                    <span class="label label-success">${item.status_name}</span>
                                                                </c:when>
                                                                <c:when test="${item.status_name == 'Đang thực hiện'}">
                                                                    <span class="label label-warning">${item.status_name}</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-default">${item.status_name}</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <c:if test="${canManage}">
                                                            <td>
                                                                <a href="${ctx}/task-edit?id=${item.id}" class="btn btn-sm btn-primary" id="btn-task-edit-${item.id}">
                                                                    <i class="fa fa-pencil"></i> Sửa
                                                                </a>
                                                                <a href="${ctx}/task-delete?id=${item.id}"
                                                                   class="btn btn-sm btn-danger"
                                                                   id="btn-task-delete-${item.id}"
                                                                   onclick="return confirm('Bạn có chắc muốn xóa tác vụ này không?')">
                                                                    <i class="fa fa-trash"></i> Xóa
                                                                </a>
                                                            </td>
                                                        </c:if>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
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
            <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
            <!--Wave Effects -->
            <script src="${ctx}/js/waves.js"></script>
            <!-- Custom Theme JavaScript -->
            <script src="${ctx}/js/custom.min.js"></script>
            <script>
                $(document).ready(function () {
                    $('#taskTable').DataTable({
                        "language": {
                            "search": "Tìm kiếm:",
                            "lengthMenu": "Hiển thị _MENU_ bản ghi",
                            "info": "Hiển thị _START_ đến _END_ trong tổng số _TOTAL_ bản ghi",
                            "paginate": {
                                "first": "Đầu",
                                "last": "Cuối",
                                "next": "Tiếp",
                                "previous": "Trước"
                            }
                        }
                    });
                });
            </script>
            <!-- Flash message from session after redirect -->
            <c:if test="${not empty flashMessage}">
                <script>
                    window.addEventListener('DOMContentLoaded', function() {
                        showToast("${flashMessage}", "${flashSuccess ? 'success' : 'error'}");
                    });
                </script>
            </c:if>
        </body>

        </html>