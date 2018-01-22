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
				<h1>単位</h1>
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
                                        <a href="/DataManager/type/middle/toTypePage/" 	class="btn btn-default">中分類</a>
                                        <a href="/DataManager/type/small/toTypePage/" class="btn btn-default">小分類</a>
                                        <a href="/DataManager/type/toUnitPage" class="btn btn-default  bg-yellow active">単位</a>
									</div>
								</div>
                                <div class="col-md-2">
                                    <input type="button" class="btn btn-block btn-success" onclick="addGoodsUnit()" value="新規追加">
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
											<th>単位名</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="goodsUnitData">
										<c:forEach var="tempUnit" items="${masterGoodsUnitList}" varStatus="status">
                                            <tr id="${tempUnit.goodsUnitId}">
                                                <td><c:out value="${status.count}"/></td>
                                                <td><input  name="tempUnitName-${tempUnit.goodsUnitId}" id="tempUnitName-${tempUnit.goodsUnitId}" class="tdwidth120" type="text" placeholder="単位" value="${tempUnit.goodsUnitName}"/></td>
                                                <td>
                                                    <button onclick="updateGoodsUnit(${tempUnit.goodsUnitId})" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;
                                                    <button onclick="deleteGoodsUnit(${tempUnit.goodsUnitId})" class="btn btn-danger operation-button-btn">削除</button>
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


        //出庫修正
        function updateGoodsUnit(obj) {
            //キーワード
            console.log("goodsUnitId  Id="+ obj);
            var goodsUnitId=obj;
            var tmpGoodsUnitName=document.getElementById("tempUnitName-"+goodsUnitId).value;

            //大分類を修正
            $.post("/DataManager/type/updateGoodsUnitName", {"goodsUnitId": goodsUnitId,"tmpGoodsUnitName": tmpGoodsUnitName}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
            });
        }

        //出庫削除
        function deleteGoodsUnit(obj) {
            //キーワード
            console.log("goodsUnitId  Id="+ obj);
            var goodsUnitId=obj;

            if (!confirm("この行を削除しますか？"))

                return;
            $.post("/DataManager/type/deleteGoodsUnitName", {"goodsUnitId": goodsUnitId}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeGoodsUnit(items);
            });


        }

        //商品リストを取得
        function makeGoodsUnit(items) {

            $("#goodsUnitData").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {

                var goodsUnitId="tempUnitName-"+items[i].goodsUnitId;

                htmlContent =
                        htmlContent + '' +
                        '<tr id="' + items[i].goodsUnitId + '">' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="大分類名"  name="'+goodsUnitId+'"  id="'+goodsUnitId+'" value="' + items[i].goodsUnitName + '"/></td>' +
                        '<td>' +
                        '<button onclick="updateGoodsUnit(' + items[i].goodsUnitId + ')" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;'+
                        '<button onclick="deleteGoodsUnit(' + items[i].goodsUnitId + ')" class="btn btn-danger operation-button-btn">削除</button>'
                '</td>' +
                '</tr>';
            }
            $("#goodsUnitData").html(htmlContent);
        }

        //大分類を追加する
        function addGoodsUnit() {

            //大分類を追加する
            $.post("/DataManager/type/addGoodsUnit", {}, function (data) {
                if (data == "login") {
                    logoutFun();
                }

                var items = JSON.parse(data);
                makeGoodsUnit(items);
            });
        }

    </script>

</body>
</html>