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
    .tdwidth70 {
        width :70pt;
    }
    .tdwidth90 {
        width :90pt;
    }
    .tdwidth120 {
        width :120pt;
    }
    .tdwidth300 {
        width :300pt;
    }
</style>
<body class="skin-green">
	<!-- header logo: style can be found in header.less -->

	<div class="wrapper row-offcanvas row-offcanvas-left">


		<aside class="">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>顧客</h1>
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
                                    <input type="button" class="btn btn-block btn-success" onclick="addClient()" value="新規追加">
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
                                            <th>名前</th>
                                            <th>電話番号</th>
                                            <th>郵便番号</th>
                                            <th>住所</th>
                                            <th>備考</th>
                                            <th>操作</th>
										</tr>
									</thead>
									<tbody id="ClientData">
										<c:forEach var="tempClient" items="${masterClientList}" varStatus="status">
                                            <tr id="${tempClient.clientId}">
                                                <td><c:out value="${status.count}"/></td>
                                                <td><input  name="tempClientName-${tempClient.clientId}" id="tempClientName-${tempClient.clientId}" class="tdwidth70" type="text" placeholder="名前" value="${tempClient.clientName}"/></td>
                                                <td><input  name="tempClientPhone-${tempClient.clientId}" id="tempClientPhone-${tempClient.clientId}" class="tdwidth90" type="text" placeholder="電話番号" value="${tempClient.clientPhone}"/></td>
                                                <td><input  name="tempClientZip-${tempClient.clientId}" id="tempClientZip-${tempClient.clientId}" class="tdwidth50" type="text" placeholder="郵便番号" value="${tempClient.clientZip}"/></td>
                                                <td><input  name="tempClientAddress-${tempClient.clientId}" id="tempClientAddress-${tempClient.clientId}" class="tdwidth300" type="text" placeholder="住所" value="${tempClient.clientAddress}"/></td>
                                                <td><input  name="tempClientBiko-${tempClient.clientId}" id="tempClientBiko-${tempClient.clientId}" class="tdwidth120" type="text" placeholder="備考" value="${tempClient.clientBiko}"/></td>
                                                <td>
                                                    <button onclick="updateClient(${tempClient.clientId})" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;
                                                    <button onclick="deleteClient(${tempClient.clientId})" class="btn btn-danger operation-button-btn">削除</button>
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
        function updateClient(obj) {
            //キーワード
            var clientId=obj;
            var tempClientName=document.getElementById("tempClientName-"+clientId).value;
            var tempClientPhone=document.getElementById("tempClientPhone-"+clientId).value;
            var tempClientZip=document.getElementById("tempClientZip-"+clientId).value;
            var tempClientAddress=document.getElementById("tempClientAddress-"+clientId).value;
            var tempClientBiko=document.getElementById("tempClientBiko-"+clientId).value;

            //倉庫を修正
            $.post("/DataManager/client/updateClient", {"clientId": clientId,"tempClientName": tempClientName,"tempClientPhone": tempClientPhone,"tempClientZip": tempClientZip,"tempClientAddress": tempClientAddress,"tempClientBiko": tempClientBiko}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
            });
        }

        //倉庫削除
        function deleteClient(obj) {
            //キーワード
            var clientId=obj;

            if (!confirm("この行を削除しますか？"))

                return;
            $.post("/DataManager/client/deleteClient", {"clientId": clientId}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeClient(items);
            });


        }

        //倉庫リストを取得
        function makeClient(items) {

            $("#ClientData").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {

                var clientId=items[i].clientId;
                var tempClientName="tempClientName-"+clientId;
                var tempClientPhone="tempClientPhone-"+clientId;
                var tempClientZip="tempClientZip-"+clientId;
                var tempClientAddress="tempClientAddress-"+clientId;
                var tempClientBiko="tempClientBiko-"+clientId;

                htmlContent =
                        htmlContent + '' +
                        '<tr id="' + items[i].clientId + '">' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td> <input class="tdwidth70" type="text" placeholder="名前"  name="'+tempClientName+'"  id="'+tempClientName+'" value="' + items[i].clientName + '"/></td>' +
                        '<td> <input class="tdwidth90" type="text" placeholder="電話番号"  name="'+tempClientPhone+'"  id="'+tempClientPhone+'" value="' + items[i].clientPhone + '"/></td>' +
                        '<td> <input class="tdwidth50" type="text" placeholder="郵便番号"  name="'+tempClientZip+'"  id="'+tempClientZip+'" value="' + items[i].clientZip + '"/></td>' +
                        '<td> <input class="tdwidth300" type="text" placeholder="住所"  name="'+tempClientAddress+'"  id="'+tempClientAddress+'" value="' + items[i].clientAddress + '"/></td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="備考"  name="'+tempClientBiko+'"  id="'+tempClientBiko+'" value="' + items[i].clientBiko + '"/></td>' +
                        '<td>' +
                        '<button onclick="updateClient(' + items[i].clientId + ')" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;'+
                        '<button onclick="deleteClient(' + items[i].clientId + ')" class="btn btn-danger operation-button-btn">削除</button>'
                '</td>' +
                '</tr>';
            }
            $("#ClientData").html(htmlContent);
        }

        //倉庫を追加する
        function addClient() {

            //倉庫を追加する
            $.post("/DataManager/client/addClient", {}, function (data) {
                if (data == "login") {
                    logoutFun();
                }

                var items = JSON.parse(data);
                makeClient(items);
            });
        }

    </script>

</body>
</html>