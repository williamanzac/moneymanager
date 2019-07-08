function Account(data) {
	this.id = ko.observable(data.id);
	this.name = ko.editable(data.name);
	if (!this.name()) {
		this.name.beginEdit();
	}
	this.number = ko.editable(data.number);
	if (!this.number()) {
		this.number.beginEdit();
	}
	this.available = ko.observable(data.available);
	this.balance = ko.observable(data.balance);
	this.type = ko.editable(data.type);
	if (!this.type()) {
		this.type.beginEdit();
	}
}

function Transaction(data) {
	this.id = data.id;
	this.date = data.date;
	this.amount = data.amount;
	this.otherParty = data.otherParty;
	this.descriotion = data.description;
	this.reference = data.reference;
	this.particulars = data.particulars;
	this.anslysisCode = data.analysisCode;
	if (data.category == null) {
		this.category = ko.editable();
	} else {
		this.category = ko.editable(data.category.name);
	}
	/*
	 * if (!this.category()) { this.category.beginEdit(); }
	 */
}

function BudgetEntry(data) {
	this.budgetAmount = ko.editable(data.budgetAmount);
	this.actualAmount = data.actualAmount;
	if (data.category != null) {
		this.categoryId = data.category.id;
	} else {
		this.categoryId = null;
	}
	this.month = data.forMonth;
	this.year = data.forYear;
	this.id = data.id;
}

function formatCurrency(value) {
	return "$" + value.toFixed(2);
}

function MoneyViewModel() {
	// Data
	var self = this;
	self.sections = [ "Dashboard", "Accounts", "Categories", "Payees", "Budget" ];
	self.accounts = ko.observableArray([]);
	self.chosenSectionId = ko.observable();
	self.chosenAccountId = ko.observable();
	self.newAccountName = ko.observable();
	self.chosenAccountData = ko.observable();
	self.chosenAccountTransactions = new ko.simpleGrid.viewModel({
	    data : ko.observableArray([]),
	    columns : [ {
	        headerText : "Date",
	        rowText : function(data) {
		        return moment(data.date).format('DD/MMM/YYYY');
	        }
	    }, {
	        headerText : "Amount",
	        rowText : function(data) {
		        return formatCurrency(data.amount);
	        }
	    }, {
	        headerText : "Other Party",
	        rowText : "otherParty"
	    }, {
	        headerText : "Description",
	        rowText : "description"
	    }, {
	        headerText : "Reference",
	        rowText : "reference"
	    }, {
	        headerText : "Particulars",
	        rowText : "particulars"
	    }, {
	        headerText : "Analysis Code",
	        rowText : "analysisCode"
	    }, {
	        headerText : "Category",
	        rowText : "category"
	    } ],
	    pageSize : 10
	});
	self.chosenAccountTransactions.categories = ko.observableArray([]);
	self.chosenAccountTransactions.updateTransaction = function(data) {
		// alert(ko.toJSON(data));
		$.ajax("/transactions/" + data.id + "/category/" + data.category(), {
		    type : "put",
		    success : function(result) {
			    // alert(result);
			    data = new Tansaction(result);
		    }
		});
	};
	self.selectedCategories = ko.observableArray([]);
	self.newCategoryName = ko.observable();
	self.categoryTransactions = new ko.simpleGrid.viewModel({
	    data : ko.observableArray([]),
	    columns : [ {
	        headerText : "Date",
	        rowText : function(data) {
		        return moment(data.date).format('DD/MMM/YYYY');
	        }
	    }, {
	        headerText : "Amount",
	        rowText : function(data) {
		        return formatCurrency(data.amount);
	        }
	    }, {
	        headerText : "Other Party",
	        rowText : "otherParty"
	    }, {
	        headerText : "Description",
	        rowText : "description"
	    }, {
	        headerText : "Reference",
	        rowText : "reference"
	    }, {
	        headerText : "Particulars",
	        rowText : "particulars"
	    }, {
	        headerText : "Analysis Code",
	        rowText : "analysisCode"
	    }, ],
	    pageSize : 10
	});
	self.rootCategory = ko.observable();

	self.budgetEntries = new ko.budgetGrid.viewModel({});
	// self.budgetEntries.categoryTree = ko.observable();
	// self.budgetEntries.data = ko.observableArray([]);

	self.fileData = ko.observable({
	    dataURL : ko.observable(),
	    base64String : ko.observable(),
	    text : ko.observable()
	});
	self.fileData().text.subscribe(function(data) {
		// alert(data);
		$.ajax("/accounts/" + self.chosenAccountId() + "/transactions", {
		    data : data,
		    type : "put",
		    contentType : "text/csv",
		    success : function(result) {
			    // alert(result);
			    location.reload();
		    }
		});
	});

	// Behaviours
	self.goToSection = function(section) {
		location.hash = section
	};
	self.goToAccount = function(account) {
		location.hash = "Accounts/" + account.id()
	};
	self.getAccounts = function() {
		$.getJSON("/accounts", function(allData) {
			var mapped = $.map(allData, function(item) {
				return new Account(item)
			});
			self.accounts(mapped);
		});
	};
	self.newAccount = function() {
		// alert(ko.toJSON({ name: self.newAccountName() }));
		$.ajax("/accounts", {
		    data : ko.toJSON({
			    name : self.newAccountName()
		    }),
		    type : "post",
		    contentType : "application/json",
		    dataType : "json",
		    success : function(result) {
			    // alert(result.name);
			    var acc = new Account(result);
			    self.accounts.push(acc);
		    }
		});
		self.newAccountName("");
	};
	self.updateAccount = function() {
		// alert(ko.toJSON(self.chosenAccountData()));
		$.ajax("/accounts", {
		    data : ko.toJSON(self.chosenAccountData()),
		    type : "put",
		    contentType : "application/json",
		    dataType : "json",
		    success : function(result) {
			    var acc = new Account(result);
			    self.chosenAccountData(acc);
			    self.getAccounts();
		    }
		});
	};
	self.getCategoryTree = function() {
		$.getJSON("/categories/tree", function(data) {
			// self.categories.data = data;
			self.selectedCategories([]);
			$("#categoryTree").treeview({
				data : data
			});
			$("#categoryTree").on('nodeSelected', function(event, node) {
				var selected = $("#categoryTree").treeview("getSelected");
				self.selectedCategories(selected);
				// alert(ko.toJSON(node));
				location.hash = "Categories/" + node.nodeId;
			});
			// alert(data[0].text);
			self.rootCategory(data[0]);
			self.budgetEntries.categoryTree(data[0]);
		});
		$.getJSON("/categories", function(allData) {
			var mapped = $.map(allData, function(item) {
				return item.name;
			});
			self.chosenAccountTransactions.categories(mapped);
			// self.chosenAccountTransactions.categories(allData);
		});
	};
	self.newCategory = function() {
		// alert(ko.toJSON({ name: self.newCategoryName() }));
		$.ajax("/categories/" + self.selectedCategories()[0].id, {
		    data : ko.toJSON({
			    name : self.newCategoryName()
		    }),
		    type : "post",
		    contentType : "application/json",
		    dataType : "json",
		    success : function(result) {
			    // alert(result.name);
			    self.getCategoryTree();
		    }
		});
		self.newCategoryName("");
	};
	self.getBudget = function() {
		$.getJSON("/budget/entries", function(allData) {
			var mapped = $.map(allData, function(item) {
				return new BudgetEntry(item);
			});
			self.budgetEntries.data(mapped);
		});
	};

	self.getCategoryTree();
	self.getAccounts();
	self.getBudget();

	// Client-side routes
	Sammy(function() {
		this.get('#:section', function() {
			self.chosenSectionId(this.params.section);
			self.chosenAccountId(null);
			self.chosenAccountData(null);
		});
		this.get("#Accounts/:account", function() {
			self.chosenSectionId("Accounts");
			self.chosenAccountId(this.params.account);
			// get account entries
			$.getJSON("/accounts/" + this.params.account, function(item) {
				var acc = new Account(item);
				self.chosenAccountData(acc);
			});
			$.getJSON("/accounts/" + this.params.account + "/transactions", function(data) {
				var mapped = $.map(data, function(item) {
					return new Transaction(item)
				});
				self.chosenAccountTransactions.data(mapped);
				// self.chosenAccountTransactions.data(data);
			});
		});
		this.get("#Categories/:category", function() {
			self.chosenSectionId("Categories");
			var node = $("#categoryTree").treeview("getNode", this.params.category);
			$("#categoryTree").treeview("selectNode", node, {
				silent : true
			})
			$.getJSON("/categories/" + node.id + "/transactions", function(data) {
				self.categoryTransactions.data(data);
				var selected = self.selectedCategories();
				// alert(ko.toJSON(selected));
			});
		});

		this.get('', function() {
			this.app.runRoute('get', '#Dashboard')
		});
	}).run();
};

ko.applyBindings(new MoneyViewModel());