<%--
  User: Rui
  Date: 2017/12/27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../htmlframe/headFrame.jsp"></jsp:include>
<jsp:include page="../../htmlframe/headerFrame.jsp"></jsp:include>
<style type="text/css">
    /*スクロール用*/
    .tdwidth50 {
        width :50pt;
    }
    .tdwidth120 {
        width :120pt;
    }
</style>
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
                                        <a href="/DataManager/type/big/toTypePage/" class="btn btn-default">大分類</a>
                                        <a href="/DataManager/type/middle/toTypePage/" 	class="btn btn-default bg-yellow active">中分類</a>
                                        <a href="/DataManager/type/small/toTypePage/" class="btn btn-default">小分類</a>
                                        <a href="/DataManager/type/toGoodsUnitPage" class="btn btn-default">単位</a>
									</div>
								</div>
							</div>
						</div>
						<div class="tab-content">
							<div class="row">
								<div class="col-md-2">
                                    <select name="big_type" id="big_type" style="width: 200px">
                                        <option value="0">大分類</option>
                                        <c:forEach items="${masterBigtypeList}" var="tempBigtype">
                                            <option value="${tempBigtype.bigtypeId}">${tempBigtype.bigtypeName}</option>
                                        </c:forEach>
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
									<tbody id="middleTypeData">
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