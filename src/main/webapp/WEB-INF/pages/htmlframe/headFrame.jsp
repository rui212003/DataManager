<%--
  User: Rui
  Date: 2017/12/27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<header class="header">

	<nav class="navbar-dm">
		<div class="container">
			<!-- <div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">在庫管理システム </a>
			</div> -->

			<div class="collapse navbar-collapse" id="nav_target">
				<!-- menu部分 ================ -->
				<ul class="nav navbar-nav">
					<!-- 入庫 -->
					<li class="dropdown"><a href="/DataManager/" class="dropdown-toggle" data-toggle="dropdown">
							入庫管理<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="/DataManager/">入庫一覧</a></li>
							<li><a href="/DataManager/input/toAddPage">入庫追加</a></li>
						</ul></li>
					<!-- 出庫 -->
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
							出庫管理<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="/DataManager/output/toOutputList">出庫一覧</a></li>
							<li><a href="/DataManager/output/toAddPage">出庫追加</a></li>
						</ul></li>
					<!-- 在庫 -->
					<li><a href="#">在庫管理</a></li>
					<!-- マスタ -->
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Master<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="#">商品名称</a></li>
							<li><a href="/DataManager/type/big/toTypePage/">商品分類</a></li>
							<%--<li><a href="/DataManager/type/toGoodsUnitPage">商品单位</a></li>--%>
							<li><a href="#">顧客</a></li>
							<li><a href="#">倉庫一覧</a></li>
						</ul></li>
					<!-- ダウンロード -->
					<li><a href="#">ダウンロード</a></li>

					<!-- 右寄せになる部分 ================ -->
					<li>zui</li>
					<!-- ログアウト -->
					<li><a href="/DataManager/logout">ログアウト</a></li>
				</ul>
			</div>
		</div>
	</nav>
</header>
<script type="text/javascript">
    $(document).ready(function(){

    });

    //Logout 関数
    function logoutFun(){
        var href = window.location.href ;
        console.log("href="+href);
        var tmp=href.split("SK");
        var newURL="";
//        for(i=0;i<tmp.length;i++){
//            console.log("tmp="+i+""    +tmp[i]);
//        }
        if(tmp.length>=1){
            newURL= tmp[0]+"SK";
        }else{
            newURL="https://valdac.power-science.com/SK/";
        }
        console.log("newURL="+newURL);
        window.location.href =newURL;
    }


    // str: 日付文字列（yyyy-MM-dd, yyyy/MM/dd）
    // delim: 区切り文字（"-", "/"など）
    function isDate (str, delim) {
        var arr = str.split(delim);
        if (arr.length !== 3) return false;
        const date = new Date(arr[0], arr[1] - 1, arr[2]);
        if (arr[0] !== String(date.getFullYear()) || arr[1] !== ('0' + (date.getMonth() + 1)).slice(-2) || arr[2] !== ('0' + date.getDate()).slice(-2)) {
            return false;
        } else {
            return true;
        }
    };
</script>