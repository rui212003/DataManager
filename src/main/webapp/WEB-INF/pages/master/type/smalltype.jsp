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
<body class="skin-green">
	<!-- header logo: style can be found in header.less -->

	<div class="wrapper row-offcanvas row-offcanvas-left">


		<aside class="">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>小分類</h1>
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
                                        <a href="/DataManager/type/small/toTypePage/" class="btn btn-default bg-yellow active">小分類</a>
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
								<div class="col-md-2">
                                    <select name="middle_type" id="middle_type" style="width: 200px">
                                        <option value="0">中分類</option>
                                    </select>
								</div>
								<span STYLE="color: red;">※大分類、中分類を選択してください</span>
							</div>

						</div>

						<div class="tab-content">
							<div class="col-md-2">
								<input type="button" class="btn btn-block btn-success" onclick="addSmalltype()" value="新規追加">
							</div>
						</div>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<table class="table table-hover valve-table">
									<thead>
										<tr>
											<th>No</th>
											<th>小分類</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="smallTypeData">
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

        //大分類により中分類を取得
        $("#big_type").change(function () {
            var bigtypeValue = $("[name=big_type]").val();
            if ("0"==bigtypeValue) {
                bigtypeValue = "";
                alert("大分類を選択してください");
            }else{
                //設定
                $("#middle_type").val("");
                console.log("bittypeValue=" + bigtypeValue);
                $.post("/DataManager/type/getMiddleTypeByBigType", {"outInput": "4","bigtypeValue": bigtypeValue}, function (data) {
                    if (data == "login") {
                        logoutFun();
                    }
                    var items = JSON.parse(data);

                    $("#middle_type").html("");
                    var htmlContent = "<option value='0'>中分類</option>";
                    for (var i = 0; i < items.length; i++) {
                        htmlContent =
                                htmlContent + '' +
                                '<option value=' + items[i].middletypeId + '>' + items[i].middletypeName + '</option>'
                    }
                    $("#middle_type").html(htmlContent);

                });
            }

        });

        //中分類により小分類を取得
        $("#middle_type").change(function () {
            var bigtypeValue = $("[name=big_type]").val();
            var middletypeValue = $("[name=middle_type]").val();
            if ("0"==bigtypeValue ||"0"==middletypeValue) {
                bigtypeValue = "";
                alert("大分類と中分類を選択してください");
            }else{
                //設定
                $("#smallTypeData").val("");

                $.post("/DataManager/type/getSmallTypeByBigType", {"outInput": "3","bigtypeValue": bigtypeValue,"middletypeValue": middletypeValue}, function (data) {
                    if (data == "login") {
                        logoutFun();
                    }
                    var items = JSON.parse(data);
                    makeSmallType(items);

                });
            }

        });

        //小分類リストを取得
        function makeSmallType(items) {

            $("#smallTypeData").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {

                var smalltypeName="smalltypeName-"+items[i].smalltypeId;

                htmlContent =
                        htmlContent + '' +
                        '<tr id="' + items[i].smalltypeId + '">' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="小分類名"  name="'+smalltypeName+'"  id="'+smalltypeName+'" value="' + items[i].smalltypeName + '"/></td>' +
                        '<td>' +
                        '<button onclick="updateSmalltype(' + items[i].smalltypeId + ')" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;'+
                        '<button onclick="deleteSmalltype(' + items[i].smalltypeId + ')" class="btn btn-danger operation-button-btn">削除</button>'
                '</td>' +
                '</tr>';
            }
            $("#smallTypeData").html(htmlContent);
        }

        //小分類修正
        function updateSmalltype(obj) {
            //キーワード
            var smalltypeId=obj;
            var tmpsmalltypeName=document.getElementById("smalltypeName-"+smalltypeId).value;

            //小分類を修正
            $.post("/DataManager/type/updateSmalltypeName", {"smalltypeId": smalltypeId,"tmpsmalltypeName": tmpsmalltypeName}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
            });
        }

        //小分類削除
        function deleteSmalltype(obj) {
            //キーワード

            var smalltypeId=obj;

            if (!confirm("この行を削除しますか？"))

                return;
            $.post("/DataManager/type/deleteSmalltypeName", {"smalltypeId": smalltypeId}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeSmallType(items);
            });
        }

        //小分類を追加する
        function addSmalltype() {
            var bigtypeValue = $("[name=big_type]").val();
            var middletypeValue = $("[name=middle_type]").val();

            if ("0"==bigtypeValue ||"0"==middletypeValue) {
                bigtypeValue = "";
                alert("大分類と中分類を選択してください");
            }else{
                //小分類を追加する
                $.post("/DataManager/type/addSmalltype", {"bigtypeId": bigtypeValue,"middletypeId": middletypeValue}, function (data) {
                    if (data == "login") {
                        logoutFun();
                    }

                    var items = JSON.parse(data);
                    makeSmallType(items);
                });
            }


        }

    </script>

</body>
</html>