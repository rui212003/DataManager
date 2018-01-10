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
				<h1>商品名称</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<!-- collection -->
				<div class="col-xs-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<div class="row">
								<div class="col-md-2">
									<select class="form-control" value="">
										<option>大分類</option>
										<option>化粧品</option>
										<option>日用品</option>
									</select>
								</div>
								<div class="col-md-2">
									<select class="form-control" value="">
										<option>中分類</option>
										<option>資生堂</option>
										<option>POLA</option>
									</select>
								</div>
								<div class="col-md-2">
									<select class="form-control" value="">
										<option>小分類</option>
										<option>CPB</option>
										<option>悦薇</option>
									</select>
								</div>
							</div>

						</div>
						<div class="tab-content">
							<div class="col-md-2">
								<input type="button"  class="btn btn-block btn-success"  value="新規追加">
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
											<th>小分類</th>
											<th>商品名</th>
											<th>バーコード</th>
											<th>単位</th>
											<th>定価(円)</th>
											<th>売価額(元)</th>
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
			//The Calender
			$('#sandbox-container .input-daterange').datepicker({
				format : 'yyyy/mm/dd',
				language : 'ja',
				autoclose : true,
				todayHighlight : true
			});
		});

		//入庫検索
		function searchInputData() {
			//キーワード
			var bigType = "化粧品";

			$.post("/DataManagement/getInputData", {
				"bigType" : bigType
			}, function(data) {
				//結果を表示する inputData

			});

		}
	</script>

</body>
</html>