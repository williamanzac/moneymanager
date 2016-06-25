<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<title>Money Manager</title>
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
		<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous"> -->
		<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"> -->
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.11/css/dataTables.bootstrap.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/select/1.1.2/css/select.bootstrap.min.css">
		<link rel="stylesheet" href="/css/knockout-file-bindings.css" />

		<link rel="stylesheet" href="/css/style.css" />
	</head>
	<body>
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Money Manager</a>
				</div>
				<div id="navbar" class="collapse navbar-collapse">
					<ul class="nav navbar-nav" data-bind="foreach: sections">
						<li data-bind="css: {active: $data == $root.chosenSectionId()}">
							<a data-bind="text: $data, click: $root.goToSection"></a>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</nav>

		<div class="container">
			<div class="page-header">
				<h1 data-bind="text: chosenSectionId()"></h1>
			</div>
			<div class="row" id="mainRow">
				<div class="col-xs-12 col-md-2" id="accounts" data-bind="visible: chosenSectionId() == 'Accounts'">
    				<div class="input-group">
    					<input type="text" class="form-control" placeholder="Account Name" data-bind="value: newAccountName, valueUpdate: 'afterkeydown'" />
    					<span class="input-group-btn">
							<button type="button" class="btn btn-success" data-bind="click: newAccount, enable: newAccountName()">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
							</button>
						</span>
					</div>
					<ul class="nav nav-pills nav-stacked" data-bind="foreach: accounts" id="accountList">
						<li data-bind="css: {active: $data.id() == $root.chosenAccountId()}">
							<a data-bind="text: $data.name, click: $root.goToAccount"></a>
						</li>
					</ul>
				</div>
				<div class="col-xs-12 col-md-10" id="accountDetail" data-bind="with: chosenAccountData, visible: chosenSectionId() == 'Accounts'">
					<div class="row">
						<div class="col-xs-12 col-md-6">
							<div class="panel panel-default">
								<div class="panel-body">
				    				<div data-bind="visible: !name.isEditing()">
										<h1>
											<span data-bind="text: name"></span>
											<a class="small btn" data-bind="click: name.beginEdit">
												<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
											</a>
										</h1>
									</div>
				    				<div class="input-group input-group-lg" data-bind="visible: name.isEditing">
				    					<input type="text" class="form-control" data-bind="value: name" placeholder="Account Name" />
				    					<span class="input-group-btn">
											<button type="button" class="btn btn-danger" data-bind="click: name.cancelEdit">
												<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
											</button>
											<button type="button" class="btn btn-success" data-bind="click: name.endEdit, click: $root.updateAccount">
												<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
											</button>
										</span>
									</div>
				    				<div data-bind="visible: !number.isEditing()">
										<span data-bind="text: number"></span>
										<a class="btn" data-bind="click: number.beginEdit">
											<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										</a>
									</div>
				    				<div class="input-group" data-bind="visible: number.isEditing">
				    					<input type="text" class="form-control" data-bind="value: number" placeholder="Account Number" />
				    					<span class="input-group-btn">
											<button type="button" class="btn btn-danger" data-bind="click: number.cancelEdit">
												<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
											</button>
											<button type="button" class="btn btn-success" data-bind="click: number.endEdit, click: $root.updateAccount">
												<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
											</button>
										</span>
									</div>
				    				<div data-bind="visible: !type.isEditing()">
										<span data-bind="text: type"></span>
										<a class="btn" data-bind="click: type.beginEdit">
											<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										</a>
									</div>
				    				<div class="input-group" data-bind="visible: type.isEditing">
				    					<input type="text" class="form-control" data-bind="value: type" placeholder="Account Type" />
				    					<span class="input-group-btn">
											<button type="button" class="btn btn-danger" data-bind="click: type.cancelEdit">
												<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
											</button>
											<button type="button" class="btn btn-success" data-bind="click: type.endEdit, click: $root.updateAccount">
												<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
											</button>
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-md-6">
							<div class="panel panel-default">
								<div class="panel-body">
									<p>Available: <span data-bind="text: formatCurrency(available())"></span></p>
									<p>Balance: <span data-bind="text: formatCurrency(balance())"></span></p>
									<div class="well" data-bind="fileDrag: $root.fileData">
									    <div class="form-group row">
									        <div class="col-md-4">
								                <label class="drag-label">Drag file here</label>
									        </div>
									        <div class="col-md-8">
									            <input type="file" data-bind="fileInput: $root.fileData, customFileInput: {
									              buttonClass: 'btn btn-default',
									              fileNameClass: 'disabled form-control',
									              buttonText: 'Browse...',
									              noFileText: 'Upload Transactions',
									              clearButton: false
									            }" accept="text/csv">
									        </div>
									    </div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-default">
								<div class="panel-body" data-bind="simpleGrid: $root.chosenAccountTransactions, simpleGridTemplate: 'transactionsGrid', simpleGridPagerTemplate: 'transactionsPager'">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-md-2" id="categories" data-bind="visible: chosenSectionId() == 'Categories'">
    				<div class="input-group">
    					<input type="text" class="form-control" placeholder="Category Name" data-bind="value: newCategoryName, valueUpdate: 'afterkeydown', enable: selectedCategories().length > 0" />
    					<span class="input-group-btn">
							<button type="button" class="btn btn-success" data-bind="click: newCategory, enable: newCategoryName() && selectedCategories().length > 0">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
							</button>
						</span>
					</div>
					<div id="categoryTree">
					</div>
				</div>
				<div class="col-xs-12 col-md-10>
					<div class="panel panel-default">
						<div class="panel-body" data-bind="simpleGrid: $root.categoryTransactions, simpleGridTemplate: 'transactionsGrid', simpleGridPagerTemplate: 'transactionsPager'">
						</div>
					</div>
				</div>
			</div>
		</div>

		<script type="text/html" id="transactionsGrid">
			<div class="row">
				<table class="table table-hover table-stripped">
					<thead>
						<tr data-bind="foreach: columns">
							<th data-bind="text: headerText"></th>
						</tr>
					</thead>
					<tbody data-bind="foreach: itemsOnCurrentPage">
						<tr data-bind="foreach: $parent.columns">
							<td data-bind="text: typeof rowText == 'function' ? rowText($parent) : $parent[rowText]"></td>
						</tr>
					</tbody>
				</table>
			</div>
		</script>
		<script type="text/html" id="transactionsPager">
			<div class="row">
				<div class="btn-group pull-right" id="transactionsPagerButtons">
					<button class="btn btn-default" type="button" data-bind="click: function() { $root.currentPageIndex($root.currentPageIndex() - 1) }, enable: currentPageIndex() > 0">
						<span class="glyphicon glyphicon-chevron-left"></span>
					</button>
					<!-- ko foreach: ko.utils.range(0, maxPageIndex) -->
						<button class="btn btn-default" type="button" data-bind="text: $data + 1, click: function() { $root.currentPageIndex($data) }, css: { active: $data == $root.currentPageIndex() }">
						</button>
					<!-- /ko -->
					<button class="btn btn-default" type="button" data-bind="click: function() { $root.currentPageIndex($root.currentPageIndex() + 1) }, enable: currentPageIndex() < maxPageIndex()">
						<span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</div>
				<div class="form-group">
					Show <select data-bind="options: [10,25,50,100], value: pageSize"></select> entries
				</div>
			</div>
		</script>
		<script type="text/html" id="treeNode">
			<li class="list-group-item">
				<span class="glyphicon glyphicon-plus"></span>
				<span data-bind="text: name"></span>
			</li>
			<!-- ko template: {name: 'treeNode', foreach: subCategories} -->
			<!-- /ko -->
		</script>

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/knockout/3.4.0/knockout-min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/knockout.mapping/2.4.1/knockout.mapping.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/sammy.js/0.7.6/sammy.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
		<script src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>
		<script src="https://cdn.datatables.net/1.10.11/js/dataTables.bootstrap.min.js"></script>
		<script src="https://cdn.datatables.net/select/1.1.2/js/dataTables.select.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.min.js"></script>
		<script src="/js/knockout-file-bindings.js"></script>
		<script src="/js/knockout.simpleGrid.3.0.js"></script>
		<script src="/js/knockout.editable.js"></script>
		<script src="/js/knockout.treeview.js"></script>
		<script src="/js/main.js"></script>
	</body>
</html>