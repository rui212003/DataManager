<%--
  User: Rui
  Date: 2017/12/27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../htmlframe/headFrame.jsp"></jsp:include>
<jsp:include page="../htmlframe/headerFrame.jsp"></jsp:include>
<body class="skin-green">
	<!-- header logo: style can be found in header.less -->

	<div class="wrapper row-offcanvas row-offcanvas-left">


		<aside class="">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>出庫追加</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<!-- collection -->
				<div class="col-xs-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<form id="searchInput" name="searchInput" method="post">
								<div class="row form-group">
									<div class="col-md-2">
										<input type="text" class="form-control" name="big_type" id="big_type" placeholder="大分類" />
									</div>
									<div class="col-md-2">
										<input type="text" class="form-control" name="middle_type" id="middle_type" placeholder="中分類" />
									</div>
									<div class="col-md-2">
										<input type="text" class="form-control" name="small_type" id="small_type" placeholder="小分類" />
									</div>
								</div>

								<div class="row form-group">
									<div class="col-md-6" id="sandbox-container">
										<div class="input-daterange input-group" id="datepicker" style="width: 100%">
											<div class="input-group-addon">
												<i class="fa fa-calendar"></i>
											</div>
											<input type="text" class="form-control" name="bgnYmd" id="bgnYmd" placeholder="開始日付">
											<span class="input-group-addon">〜</span>
											<input type="text" class="form-control" name="endYmd" id="endYmd" placeholder="終了日付">
										</div>
									</div>
									<div class="col-md-2">
										<input type="button" id="master-location-confirm" class="btn btn-block btn-success" onclick="searchInputData()" value="検索">
									</div>
								</div>

							</form>
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
											<th>数量</th>
											<th>単位</th>
											<th>円定価</th>
											<th>出庫日付</th>
											<th>追跡番号</th>
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