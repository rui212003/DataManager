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
				<h1>倉庫</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<!-- collection -->
				<div class="col-xs-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<div class="row">
								<!-- collection -->
                                <div class="col-md-2">
                                    <input type="button" class="btn btn-block btn-success" onclick="addWarehouse()" value="新規追加">
                                </div>
							</div>
						</div>
						<%--<div class="tab-content">--%>
							<%----%>
						<%--</div>--%>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<table class="table table-hover valve-table">
									<thead>
										<tr>
											<th>No</th>
											<th>倉庫名</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="WarehouseData">
										<c:forEach var="tempWarehouse" items="${masterWarehouseList}" varStatus="status">
                                            <tr id="${tempWarehouse.warehouseId}">
                                                <td><c:out value="${status.count}"/></td>
                                                <td><input  name="tempWarehouseName-${tempWarehouse.warehouseId}" id="tempWarehouseName-${tempWarehouse.warehouseId}" class="tdwidth120" type="text" placeholder="倉庫名" value="${tempWarehouse.warehouseName}"/></td>
                                                <td>
                                                    <button onclick="updateWarehouse(${tempWarehouse.warehouseId})" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;
                                                    <button onclick="deleteWarehouse(${tempWarehouse.warehouseId})" class="btn btn-danger operation-button-btn">削除</button>
                                                </td>
                                            </tr>
										</c:forEach>
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


        //倉庫修正
        function updateWarehouse(obj) {
            //キーワード
            console.log("goodsUnitId  Id="+ obj);
            var warehouseId=obj;
            var tmpWarehouseName=document.getElementById("tempWarehouseName-"+warehouseId).value;

            //倉庫を修正
            $.post("/DataManager/type/updateWarehouseName", {"warehouseId": warehouseId,"tmpWarehouseName": tmpWarehouseName}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
            });
        }

        //倉庫削除
        function deleteWarehouse(obj) {
            //キーワード
            console.log("warehouseId  Id="+ obj);
            var warehouseId=obj;

            if (!confirm("この行を削除しますか？"))

                return;
            $.post("/DataManager/type/deleteWarehouseName", {"warehouseId": warehouseId}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeWarehouse(items);
            });


        }

        //倉庫リストを取得
        function makeWarehouse(items) {

            $("#WarehouseData").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {

                var warehouseId="tempWarehouseName-"+items[i].warehouseId;

                htmlContent =
                        htmlContent + '' +
                        '<tr id="' + items[i].warehouseId + '">' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="倉庫名"  name="'+warehouseId+'"  id="'+warehouseId+'" value="' + items[i].warehouseName + '"/></td>' +
                        '<td>' +
                        '<button onclick="updateWarehouse(' + items[i].warehouseId + ')" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;'+
                        '<button onclick="deleteWarehouse(' + items[i].warehouseId + ')" class="btn btn-danger operation-button-btn">削除</button>'
                '</td>' +
                '</tr>';
            }
            $("#WarehouseData").html(htmlContent);
        }

        //倉庫を追加する
        function addWarehouse() {

            //倉庫を追加する
            $.post("/DataManager/type/addWarehouse", {}, function (data) {
                if (data == "login") {
                    logoutFun();
                }

                var items = JSON.parse(data);
                makeWarehouse(items);
            });
        }

    </script>

</body>
</html>