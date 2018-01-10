<%--
  User: Rui
  Date: 2017/12/27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../htmlframe/headFrame.jsp"></jsp:include>
<jsp:include page="../../htmlframe/headerFrame.jsp"></jsp:include>
<body class="skin-green">
	<!-- header logo: style can be found in header.less -->

	<div class="wrapper row-offcanvas row-offcanvas-left">


		<aside class="">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>中分類</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<!-- collection -->
				<div class="col-xs-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<div class="row">
								<!-- collection -->
								<div class="col-md-4">
									<div class="btn-group" role="group">
										<a href="/DataManagement/html/pages/master/type/bigtype.jsp" class="btn btn-default">大分類</a>
										<a href="/DataManagement/html/pages/master/type/middletype.jsp" class="btn btn-default bg-yellow active">中分類</a>
										<a href="/DataManagement/html/pages/master/type/smalltype.jsp" class="btn btn-default">小分類</a>
										<a href="/DataManagement/html/pages/master/type/unit.jsp" class="btn btn-default">単位</a>
									</div>
								</div>
							</div>
						</div>
						<div class="tab-content">
							<div class="row">
								<div class="col-md-2">
									<select class="form-control" value="">
										<option>大分類</option>
										<option>化粧品</option>
										<option>日用品</option>
									</select>
								</div>
								<span STYLE="color: red;">※大分類を選択してください</span>
							</div>
						</div>
						<div class="tab-content">
							<div class="col-md-2">
								<input type="button" class="btn btn-block btn-success" value="新規追加">
							</div>
						</div>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<table class="table table-hover valve-table">
									<thead>
										<tr>
											<th>No</th>
											<th>大分類</th>
											<th>中分類</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="inputData">
									</tbody>
								</table>
							</div>
							<!-- /.tab-pane -->
						</div>
						<!-- /.tab-content -->
					</div>
				</div>
			</section>
			<!-- /.content -->
		</aside>
		<!-- /.right-side -->
	</div>
	<!-- ./wrapper -->

	<!-- add new calendar event modal -->

	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>

</body>
</html>