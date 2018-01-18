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
				<h1>出庫追加</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<!-- collection -->
				<div class="col-xs-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<form id="searchOutput" name="searchOutput" method="post">
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
											<th>元定価</th>
                                            <th>折扣</th>
											<th>出庫日付</th>
											<th>追跡番号</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="outputData">
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
//                    $.post("/DataManager/output/addOutputFile", {"outputfileName": reader.result}, function (data) {
//                        if (data == "login") {
//                            logoutFun();
//                        }
//                        var items = JSON.parse(data);
//                        makeUpGoodsType(items);
//                    });
                    var outputfileName=reader.result;

                    $.ajax({
                         url : "/DataManager/output/addOutputFile",
                         type: "POST",
                         data: { "outputfileName": outputfileName},
                         async:false,
                          success : function(data) {
                             if (data.success) {
                                 alert("エラー");
                             } else {
                                 var items = JSON.parse(data);
                                 makeUpGoodsType(items);
                                 alert("DBに保存しました");
                             }
                            }
                        });
        })

            });
		});


        //商品リストを取得
        function makeUpGoodsType(items) {
            $("#outputData").html("");
            var htmlContent = "";
            for (var i = 0; i < items.length; i++) {

                var outputNumId="outputNum-"+items[i].outputListId;
                var outputDiscountId="outputDiscount-"+items[i].outputListId;
                var outputDateId="outputDate-"+items[i].outputListId;
                var outputTrackNumId="outputTrackNum-"+items[i].outputListId;

                htmlContent =
                        htmlContent + '' +
                        '<tr id="' + items[i].outputListId + '">' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td>' + items[i].outputBigtypeName + '</td>' +
                        '<td>' + items[i].outputMiddletypeName + '</td>' +
                        '<td>' + items[i].outputSmalltypeName + '</td>' +
                        '<td>' + items[i].goodsList.goodsListName + '</td>' +
                        '<td>' + items[i].goodsList.goodsBarcode + '</td>' +
                        '<td> <input class="tdwidth50" type="number" placeholder="数量"  name="'+outputNumId+'"  id="'+outputNumId+'" value="' + items[i].outputNum + '"/></td>' +
                        '<td>' + items[i].goodsList.goodsUnitName + '</td>' +
                        '<td>' + items[i].goodsList.goodsPriceCH + '</td>' +
                        '<td> <input class="tdwidth50" type="number" placeholder="折扣"  name="'+outputDiscountId+'"  id="'+outputDiscountId+'" value="' + items[i].outputDiscount + '"/></td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="出荷日付"  name="'+outputDateId+'"  id="'+outputDateId+'" value="' + items[i].outputDate + '"/></td>' +
                        '<td> <input class="tdwidth120" type="text" placeholder="追跡番号"  name="'+outputTrackNumId+'"  id="'+outputTrackNumId+'" value="' + items[i].outputTrackNum + '"/></td>' +
                        '<td>' +
                        '<button onclick="updateOutput(' + items[i].outputListId + ')" class="btn btn-primary operation-button-btn">更新</button>&nbsp;&nbsp;&nbsp;'+
                        '<button onclick="deleteOutput(' + items[i].outputListId + ')" class="btn btn-danger operation-button-btn">削除</button>'
                '</td>' +
                '</tr>';
            }
            $("#outputData").html(htmlContent);
            $('#outputData_num').text('合計 '+items.length+" 件");
            $("#outputDataList").trigger("update");
        }
	</script>

</body>
</html>

<script type="text/javascript">


    //出庫修正
    function updateOutput(obj) {
        //キーワード
        console.log("outputDataId  Id="+ obj);
        var outputDataId=obj;
        var tmpOutputNum=document.getElementById("outputNum-"+outputDataId).value;
        var tmpOutputDiscount=document.getElementById("outputDiscount-"+outputDataId).value;
        var tmpOutputDate=document.getElementById("outputDate-"+outputDataId).value;

        //大分類を選択しなかった場合は、検索しない
        if (tmpOutputNum == "0" || tmpOutputNum == "") {
            alert("数量は０より大きい数字を入力してください")
            return false;
        } else if (tmpOutputDiscount<=0 || tmpOutputDiscount>100) {
            alert("割引は１－１００間の数字を入力してください")
            return false;
        } else if (isDate(tmpOutputDate)) {
            alert("日付をyyyy-mm-dd HH:MM:SS のように正しく入力してください")
            return false;
        } else {
            $.post("/DataManager/output/updateOutput", {"outputDataId": outputDataId,"tmpOutputNum": tmpOutputNum, "tmpOutputDiscount": tmpOutputDiscount, "tmpOutputDate": tmpOutputDate}, function (data) {
                if (data == "login") {
                    logoutFun();
                }
            });
        }
    }

    //出庫削除
    function deleteOutput(obj) {
        //キーワード
        console.log("inoutDate  Id="+ obj);
        var outputDataId=obj;
        if (!confirm("この行を削除しますか？"))
            return;
        $.post("/DataManager/output/deleteOutput", {"outputDataId": outputDataId}, function (data) {
            if (data == "login") {
                logoutFun();
            }

            searchOutputData();
        });


    }
</script>