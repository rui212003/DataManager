<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<h1>入庫一覧</h1>
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
                                        <select name="big_type" id="big_type" style="width: 200px">
                                            <option>大分類</option>
                                            <c:forEach items="${bigtypeList}" var="tempBigtype">
                                                <option value="${tempBigtype.bigtypeId}">${tempBigtype.bigtypeName}</option>
                                            </c:forEach>
                                        </select>
									</div>
									<div class="col-md-2">
                                        <select name="middle_type" id="middle_type" style="width: 200px">
                                        </select>
									</div>
									<div class="col-md-2">
                                        <select name="small_type" id="small_type" style="width: 200px">
                                        </select>
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
											<th>商品名</th>
											<th>バーコード</th>
											<th>数量</th>
											<th>単位</th>
											<th>円定価</th>
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
			//The Calender
			$('#sandbox-container .input-daterange').datepicker({
				format : 'yyyy-mm-dd',
				language : 'ja',
				autoclose : true,
				todayHighlight : true
			});

            //大分類により中分類を取得
            $("#big_type").change(function(){
                var bigtypeValue = $("[name=big_type]").val();
                if(bigtypeValue == "大分類"){
                    bigtypeValue = "";
                }
                //設定
                $("#middle_type").val("");
                $("#small_type").val("");
                console.log("bittypeValue="+bigtypeValue);
                $.post("/DataManager/type/getMiddleTypeByBigType",{"bigtypeValue":bigtypeValue},function(data){
                    if(data=="login"){
                        logoutFun();
                    }
                    var items = JSON.parse(data);
                    makeUpMiddleType(items);


                    //小分類を設定
                    var middletypeValue = $("[name=middle_type]").val();
                    //sessionに保存
                    console.log("middletypeValue="+middletypeValue);
                    $.post("/DataManager/type/getSmallTypeByBigType",{"bigtypeValue":bigtypeValue,"middletypeValue":middletypeValue},function(data){
                        if(data=="login"){
                            logoutFun();
                        }
                        var items = JSON.parse(data);
                        makeUpSmallType(items);
                    });
                });
            });

            //中分類により小分類を取得
            $("#middle_type").change(function(){
                    //小分類を設定
                var bigtypeValue = $("[name=big_type]").val();
                var middletypeValue = $("[name=middle_type]").val();

                //設定
                $("#small_type").val("");
                    console.log("middletypeValue="+middletypeValue);
                    $.post("/DataManager/type/getSmallTypeByBigType",{"bigtypeValue":bigtypeValue,"middletypeValue":middletypeValue},function(data){
                        if(data=="login"){
                            logoutFun();
                        }
                        var items = JSON.parse(data);
                        makeUpSmallType(items);
                    });

            });

        });

        //中分類を取得
        function makeUpMiddleType(items){
            $("#middle_type").html("");
            var htmlContent = "";
            for(var i = 0;i<items.length;i++){
                htmlContent =
                        htmlContent + '' +
                        '<option value='+items[i].middletypeId+'>'+items[i].middletypeName+'</option>'
            }
            $("#middle_type").html(htmlContent);
        }

        //小分類を取得
        function makeUpSmallType(items){
            $("#small_type").html("");
            var htmlContent = "";
            for(var i = 0;i<items.length;i++){
                htmlContent =
                        htmlContent + '' +
                        '<option value='+items[i].smalltypeId+'>'+items[i].smalltypeName+'</option>'
            }
            $("#small_type").html(htmlContent);
        }


        //商品リストを取得
        function makeUpGoodsType(items){
            $("#inputData").html("");
            var htmlContent = "";
            for(var i = 0;i<items.length;i++){
                htmlContent =
                        htmlContent + '' +
                                '<tr id="'+items[i].inputListId+'">' +
                                '<td>'+(i+1)+'</td>' +
                                '<td>'+items[i].goodsList.goodsListName+'</td>' +
                                '<td>'+items[i].goodsList.goodsBarcode+'</td>' +
                                '<td>'+items[i].inputNum+'</td>' +
                                '<td>'+items[i].goodsList.goodsUnitName+'</td>' +
                                '<td>'+items[i].goodsList.goodsPriceJP+'</td>' +
                                '<td>'+items[i].inputDate+'</td>' +
                                '<td>'+items[i].inputTrackNum+'</td>' +
                                '<td>' +
                                '<a class="btn btn-primary btn-sm operation-button-btn" href="/DataManager/goods/'+items[i].inputListId+'"><i class="glyphicon glyphicon-pencil">編集</i></a>' +
                                '</td>' +
                                '</tr>';
            }
            $("#inputData").html(htmlContent);
        }

		//入庫検索
		function searchInputData() {
			//キーワード
            var bigtypeValue = $("[name=big_type]").val();
            var middletypeValue = $("[name=middle_type]").val();
            var smalltypeValue = $("[name=small_type]").val();
            var startDate = document.getElementById("bgnYmd").value;
            var endDate = document.getElementById("endYmd").value;

            console.log("Goods bigtypeValue ="+bigtypeValue);
            console.log("Goods middletypeValue ="+middletypeValue);
            console.log("Goods smalltypeValue ="+smalltypeValue);
            console.log("Goods startDate ="+startDate);
            console.log("Goods endDate ="+endDate);

            $.post("/DataManager/input/getGoodsListByKeywords",{"bigtypeValue":bigtypeValue,"middletypeValue":middletypeValue,"smalltypeValue":smalltypeValue,"startDate":startDate,"endDate":endDate},function(data){
                if(data=="login"){
                    logoutFun();
                }
                var items = JSON.parse(data);
                makeUpGoodsType(items);
            });



		}



	</script>

</body>
</html>