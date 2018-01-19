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
				<h1>大分類</h1>
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
										<a href="/DataManager/type/big/toTypePage/" class="btn btn-default bg-yellow active">大分類</a>
                                        <a href="/DataManager/type/middle/toTypePage/" 	class="btn btn-default">中分類</a>
                                        <a href="/DataManager/type/small/toTypePage/" class="btn btn-default">小分類</a>
                                        <a href="/DataManager/type/toGoodsUnitPage" class="btn btn-default">単位</a>
									</div>
								</div>
                                <div class="col-md-2">
                                    <input type="button" class="btn btn-block btn-success" onclick="addBigtype()" value="新規追加">
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
											<th>大分類</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="bigtypeData">
										<c:forEach var="tempbigtype" items="${masterBigtypeList}" varStatus="status">
                                            <tr id="${tempbigtype.bigtypeId}">
                                                <td><c:out value="${status.count}"/></td>
                                                <td><input  name="bigtypeName-${tempbigtype.bigtypeId}" id="bigtypeName-${tempbigtype.bigtypeId}" class="tdwidth120" type="text" placeholder="大分類名" value="${tempbigtype.bigtypeName}"/></td>
                                                <td>
                                                    <button onclick="updateBigtype(${tempbigtype.bigtypeId})" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;
                                                    <button onclick="deleteBigtype(${tempbigtype.bigtypeId})" class="btn btn-danger operation-button-btn">削除</button>
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
        function updateBigtype(obj) {
            //キーワード
            console.log("Bigtype  Id="+ obj);
            var bigtypeId=obj;
            var tmpbigtypeName=document.getElementById("bigtypeName-"+bigtypeId).value;

            //大分類を修正
            $.post("/DataManager/type/updateBigtypeName", {"bigtypeId": bigtypeId,"tmpbigtypeName": tmpbigtypeName}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
            });
        }

        //出庫削除
        function deleteBigtype(obj) {
            //キーワード
            console.log("Bigtype  Id="+ obj);
            var bigtypeId=obj;

            if (!confirm("この行を削除しますか？"))

                return;
            $.post("/DataManager/type/deleteBigtypeName", {"bigtypeId": bigtypeId}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeBigType(items);
            });


        }

        //商品リストを取得
        function makeBigType(items) {

            $("#bigtypeData").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {

                var bigtypeName="bigtypeName-"+items[i].bigtypeId;

                htmlContent =
                        htmlContent + '' +
                        '<tr id="' + items[i].bigtypeId + '">' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="大分類名"  name="'+bigtypeName+'"  id="'+bigtypeName+'" value="' + items[i].bigtypeName + '"/></td>' +
                        '<td>' +
                        '<button onclick="updateBigtype(' + items[i].bigtypeId + ')" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;'+
                        '<button onclick="deleteBigtype(' + items[i].bigtypeId + ')" class="btn btn-danger operation-button-btn">削除</button>'
                '</td>' +
                '</tr>';
            }
            $("#bigtypeData").html(htmlContent);
        }

        //大分類を追加する
        function addBigtype() {

            //大分類を追加する
            $.post("/DataManager/type/addBigtype", {}, function (data) {
                if (data == "login") {
                    logoutFun();
                }

                var items = JSON.parse(data);
                makeBigType(items);
            });
        }

    </script>

</body>
</html>