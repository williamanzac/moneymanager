(function () {
	function createTreeview(element, data){
		$(element).treeview(data);
		$(element).on('nodeSelected', function(event, node) {
			var selected = $(element).treeview("getSelected");
			value.selectedNodes(selected);
		});
	};
	
	ko.treeview = {
		viewmodel: function(configuration) {
			this.data = configuration.data;
			this.selectedNodes = ko.observableArray([]);
		}
	};

	ko.bindingHandlers.treeview = {
		init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
			// This will be called when the binding is first applied to an element
			// Set up any initial state, event handlers, etc. here
			var value = valueAccessor();
			var data = ko.unwrap(value);
			createTreeview(element, data);
		},
		update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
			// This will be called once when the binding is first applied to an element,
			// and again whenever any observables/computeds that are accessed change
			// Update the DOM element based on the supplied values here.
			var value = valueAccessor();
			var data = ko.unwrap(value);
			setInterval(createTreeview(element, data), 2000);
		}
	};
})();
/*
var TreeNode = function(data) {
	var self = this;
	self.text = data.text;
	self.icon = data.icon;
	self.selectedIcon: "glyphicon glyphicon-stop",
	self.color: "#000000",
	self.backColor: "#FFFFFF",
	self.href: "#node-1",
	self.selectable: true,
	self.state: {
	checked: true,
	disabled: true,
	expanded: true,
	selected: true
	},
	self.tags: ['available'],
	self.nodes
}*/