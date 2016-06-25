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
/*
function StatementEntry(data) {
	this.id = data.id;
	private String otherParty;
	private Date date;
	private Category category;
}
*/
function formatCurrency(value) {
	return "$" + value.toFixed(2);
}

var CategoryNode = function(data) {
	var self = this;
	self.id = data.id;
	self.text = data.name;
	self.backColor = data.colour;
	self.nodes = [];
//    var mapped = $.map(data.subCategories, function(item) { return new CategoryNode(item) });
//    this.subCategories(mapped);
	ko.mapping.fromJS(data, self.mapOptions, self);
}

CategoryNode.prototype.mapOptions = {
	nodes: {
		create: function(args) {
			return new CategoryNode(args.data);
		}
	}
};

function MoneyViewModel() {
    // Data
    var self = this;
    self.sections = ["Dashboard", "Accounts", "Categories", "Payees", "Budget"];
    self.accounts = ko.observableArray([]);
    self.chosenSectionId = ko.observable();
    self.chosenAccountId = ko.observable();
    self.newAccountName = ko.observable();
    self.chosenAccountData = ko.observable();
    self.chosenAccountTransactions = new ko.simpleGrid.viewModel({
    	data: ko.observableArray([]),
        columns: [
            { headerText: "Date", rowText: function(data) { return moment(data.date).format('DD/MMM/YYYY'); } },
            { headerText: "Amount", rowText: function(data) { return formatCurrency(data.amount); } },
            { headerText: "Other Party", rowText: "otherParty" },
            { headerText: "Description", rowText: "description" },
            { headerText: "Reference", rowText: "reference" },
            { headerText: "Particulars", rowText: "particulars" },
            { headerText: "Analysis Code", rowText: "analysisCode" },
        ],
        pageSize: 10
    });
    self.fileData = ko.observable({
    	dataURL: ko.observable(),
    	base64String: ko.observable(),
    	text: ko.observable()
    });
/*    self.categories = new ko.treeview.viewmodel({
    	data: []
    });*/
    self.selectedCategories = ko.observableArray([]);
    self.newCategoryName = ko.observable();
    self.categoryTransactions = new ko.simpleGrid.viewModel({
    	data: ko.observableArray([]),
        columns: [
            { headerText: "Date", rowText: function(data) { return moment(data.date).format('DD/MMM/YYYY'); } },
            { headerText: "Amount", rowText: function(data) { return formatCurrency(data.amount); } },
            { headerText: "Other Party", rowText: "otherParty" },
            { headerText: "Description", rowText: "description" },
            { headerText: "Reference", rowText: "reference" },
            { headerText: "Particulars", rowText: "particulars" },
            { headerText: "Analysis Code", rowText: "analysisCode" },
        ],
        pageSize: 10
    });

    self.fileData().text.subscribe(function(data){
    	//alert(data);
        $.ajax("/accounts/" + self.chosenAccountId() + "/transactions", {
            data: data,
            type: "put",
            contentType: "text/csv",
            success: function(result) {
            	//alert(result);
            	window.location.reload();
            }
        });
    });
    
    // Behaviours
    self.goToSection = function(section) { location.hash = section };
    self.goToAccount = function(account) { location.hash = "Accounts/" + account.id() };
    self.getAccounts = function () {
        $.getJSON("/accounts", function(allData) {
            var mapped = $.map(allData, function(item) { return new Account(item) });
            self.accounts(mapped);
        });
    };
    self.newAccount = function() {
    	//alert(ko.toJSON({ name: self.newAccountName() }));
        $.ajax("/accounts", {
            data: ko.toJSON({ name: self.newAccountName() }),
            type: "post",
            contentType: "application/json",
            dataType: "json",
            success: function(result) {
            	//alert(result.name);
            	var acc = new Account(result);
            	self.accounts.push(acc);
            }
        });
        self.newAccountName("");
    };
    self.updateAccount = function() {
    	//alert(ko.toJSON(self.chosenAccountData()));
        $.ajax("/accounts", {
            data: ko.toJSON(self.chosenAccountData()),
            type: "put",
            contentType: "application/json",
            dataType: "json",
            success: function(result) {
                var acc = new Account(result);
                self.chosenAccountData(acc);
                self.getAccounts();
            }
        });
    };
    self.getCategoryTree = function() {
        $.getJSON("/categories/tree", function(data) {
            //self.categories.data = data;
        	self.selectedCategories([]);
        	$("#categoryTree").treeview({
        		data: data
        	});
    		$("#categoryTree").on('nodeSelected', function(event, node) {
    			var selected = $("#categoryTree").treeview("getSelected");
    			self.selectedCategories(selected);
    			document.location("#Categories/" + node.id);
    		});
        });
    };
    self.newCategory = function() {
    	//alert(ko.toJSON({ name: self.newCategoryName() }));
        $.ajax("/categories/" + self.selectedCategories()[0].id, {
            data: ko.toJSON({ name: self.newCategoryName() }),
            type: "post",
            contentType: "application/json",
            dataType: "json",
            success: function(result) {
            	//alert(result.name);
            	self.getCategoryTree();
            }
        });
        self.newCategoryName("");
    };
    //self.goToMail = function(mail) { location.hash = mail.folder + '/' + mail.id };

    // Client-side routes    
    Sammy(function() {
        this.get('#:section', function() {
            self.chosenSectionId(this.params.section);
        	self.chosenAccountId(null);
            //self.chosenMailData(null);
            //$.get("/mail", { folder: this.params.folder }, self.chosenFolderData);
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
                self.chosenAccountTransactions.data(data);
            });
        });
        this.get("#Caregories/:category", function() {
        	self.chosenSectionId("Categories");
            $.getJSON("/categories/" + this.params.categories + "/transactions", function(data) {
                self.categoryTransactions.data(data);
            });
        });
/*
        this.get('#:folder/:mailId', function() {
            self.chosenFolderId(this.params.folder);
            self.chosenFolderData(null);
            $.get("/mail", { mailId: this.params.mailId }, self.chosenMailData);
        });
*/
        this.get('', function() { this.app.runRoute('get', '#Dashboard') });
    }).run();

/*    $.getJSON("/accounts", function(allData) {
        var mapped = $.map(allData, function(item) { return new Account(item) });
        self.accounts(mapped);
    });
    */
    self.getAccounts();
    self.getCategoryTree();
};

ko.applyBindings(new MoneyViewModel());