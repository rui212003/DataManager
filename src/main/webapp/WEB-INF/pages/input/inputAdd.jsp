<%--
  User: Rui
  Date: 2017/12/27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../htmlframe/headFrame.jsp"></jsp:include>
<jsp:include page="../htmlframe/headerFrame.jsp"></jsp:include>
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
				<h1>入庫追加</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<!-- collection -->
				<div class="col-xs-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<form id="searchInput" name="searchInput" method="post">
								<div class="row form-group">
									<div class="col-md-4">
                                        <form name="myform">
                                            <input name="myfile" id="myfile"  type="file"  name="txt読込む" /><br/>
                                            <%--<textarea name="output" id="output"  cols="80" rows="10"></textarea>--%>
                                        </form>
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
                                            <th>折扣</th>
											<th>入庫日付</th>
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
            $("#myfile").change(function (obj) {

                console.log("file upload");
                var result = obj.target.files[0];

                //FileReaderのインスタンスを作成する
                var reader = new FileReader();

                //読み込んだファイルの中身を取得する
                reader.readAsText( result );

                //ファイルの中身を取得後に処理を行う
                reader.addEventListener( 'load', function() {

                    //ファイルの中身をtextarea内に表示する
//                    $("#output").val(reader.result);

                    //ファイルをサーバに送る
                    $.post("/DataManager/input/addInputFile", {"inputfileName": reader.result}, function (data) {
                        if (data == "login") {
                            logoutFun();
                        }
                        var items = JSON.parse(data);
                        makeUpGoodsType(items);
                    });

                })

            });
		});


        //商品リストを取得
        function makeUpGoodsType(items) {
            $("#inputData").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {

                var inputNumId="inputNum-"+items[i].inputListId;
                var inputDiscountId="inputDiscount-"+items[i].inputListId;
                var inputDateId="inputDate-"+items[i].inputListId;
                var inputTrackNumId="inputTrackNum-"+items[i].inputListId;

                htmlContent =
                        htmlContent + '' +
                        '<tr id="' + items[i].inputListId + '">' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td>' + items[i].inputBigtypeName + '</td>' +
                        '<td>' + items[i].inputMiddletypeName + '</td>' +
                        '<td>' + items[i].inputSmalltypeName + '</td>' +
                        '<td>' + items[i].goodsList.goodsListName + '</td>' +
                        '<td>' + items[i].goodsList.goodsBarcode + '</td>' +
                        '<td> <input class="tdwidth50" type="number" placeholder="数量"  name="'+inputNumId+'"  id="'+inputNumId+'" value="' + items[i].inputNum + '"/></td>' +
                        '<td>' + items[i].goodsList.goodsUnitName + '</td>' +
                        '<td>' + items[i].goodsList.goodsPriceJP + '</td>' +
                        '<td> <input class="tdwidth50" type="number" placeholder="折扣"  name="'+inputDiscountId+'"  id="'+inputDiscountId+'" value="' + items[i].inputDiscount + '"/></td>' +
                        '<td>' + items[i].inputDate + '</td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="追跡番号"  name="'+inputTrackNumId+'"  id="'+inputTrackNumId+'" value="' + items[i].inputTrackNum + '"/></td>' +
                        '<td>' +
                        '<button onclick="updateInput(' + items[i].inputListId + ')" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;'+
                        '<button onclick="deleteInput(' + items[i].inputListId + ')" class="btn btn-danger operation-button-btn">削除</button>'
                '</td>' +
                '</tr>';
            }
            $("#inputData").html(htmlContent);
            $('#inputData_num').text('合計 '+items.length+" 件");
            $("#inputDataList").trigger("update");
        }
	</script>

</body>
</html>