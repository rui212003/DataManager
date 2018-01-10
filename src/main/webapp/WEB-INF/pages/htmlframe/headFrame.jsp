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
							<li><a href="#">入庫追加</a></li>
						</ul></li>
					<!-- 出庫 -->
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
							出庫管理<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="#">出庫一覧</a></li>
							<li><a href="#">出庫追加</a></li>
						</ul></li>
					<!-- 在庫 -->
					<li><a href="#">在庫管理</a></li>
					<!-- マスタ -->
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Master<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="#">商品名称</a></li>
							<li><a href="#">商品分類</a></li>
							<li><a href="#">商品单位</a></li>
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