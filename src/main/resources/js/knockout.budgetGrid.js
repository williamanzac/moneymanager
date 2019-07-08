(function() {
	// Private function
	var MonthYear = function(data) {
		this.month = data.month;
		this.year = data.year;
		
		this.header = function() {
			var date = moment({month: this.month, year: this.year});
			return date.format('MMMM YYYY');
		}
	};

	function getColumns(data) {
		var columns = [];
		ko.unwrap(data).forEach(function(item) {
			if (item.month == -1) {
				return;
			}
			var found = false;
			for (var i = 0; i < columns.length; i++) {
				if (columns[i].month == item.month && columns[i].year == item.year) {
					found = true;
					break;
				}
			}
			if (!found) {
				var column = new MonthYear(item);
				columns.push(column);
			}
		});
		return columns;
	};

	ko.budgetGrid = {
		// Defines a view model class you can use to populate a grid
		viewModel : function(configuration) {
			var self = this;
			this.data = ko.observableArray([]);
			this.currentPageIndex = ko.observable(0);
			this.pageSize = ko.observable(configuration.pageSize || 5);
			this.categoryTree = ko.observable();
			this.newYear = ko.observable();
			this.newMonth = ko.observable();

			this.columns = ko.computed(function() {
				return getColumns(this.data());
			}, this);
			
			this.years = function() {
				var y = moment().year();
				return [y, y + 1];
			};
			
			this.months = function() {
				var date = moment().startOf('year');
				var ret = [];
				for (var i = 0; i <= 11; i++) {
					ret.push({value: date.month(), label: date.format('MMMM')});
					date.add(1, 'months');
				}
				return ret;
			};

			this.getData = function(categoryId, monthYear) {
				var entry = null;
				ko.unwrap(this.data).forEach(
				        function(item) {
					        if (categoryId == item.categoryId
					                && ((item.month == -1 && monthYear == "base") || (monthYear.month == item.month && monthYear.year == item.year))) {
						        entry = item;
					        }
				        });
				return entry;
			};

			this.columnsOnCurrentPage = ko.computed(function() {
				var startIndex = this.pageSize() * this.currentPageIndex();
				return ko.unwrap(this.columns).slice(startIndex, startIndex + this.pageSize());
			}, this);

			this.maxPageIndex = ko.computed(function() {
				return Math.ceil(ko.unwrap(this.columns).length / this.pageSize()) - 1;
			}, this);

			this.updateEntry = function(entry) {
				var data = {
				    forMonth : entry.month,
				    forYear : entry.year,
				    budgetAmount : entry.budgetAmount,
				    id : entry.id
				};
				$.ajax("/budget/entries/", {
				    data : ko.toJSON(data),
				    type : "put",
				    contentType : "application/json",
				    dataType : "json",
				    success : function(result) {
					    // alert(result.name);
					    // self.getCategoryTree();
				    }
				});
			};
			
			this.createMonthEntries = function() {
				$.ajax("/budget/" + this.newYear() + "/" + this.newMonth(), {
				    type : "post",
				    success : function(result) {
					    //alert(result);
					    // self.getCategoryTree();
				    	this.data().push(result);
				    }
				});
			};
		}
	};

	// Templates used to render the grid
	var templateEngine = new ko.nativeTemplateEngine();

	templateEngine.addTemplate = function(templateName, templateMarkup) {
		document.write("<script type='text/html' id='" + templateName + "'>" + templateMarkup + "<" + "/script>");
	};

	templateEngine
	        .addTemplate(
	                "ko_budgetGrid_grid",
	                "\
			<div class=\"row\">\
                <table class=\"table table-hover table-stripped\">\
                    <thead>\
                        <tr>\
                            <th>Categories</th>\
                            <th>Base</th>\
                            <!-- ko foreach: columnsOnCurrentPage -->\
                            <th data-bind=\"text: header()\"></th>\
                            <!-- /ko -->\
                            <th>\
                            	<form class=\"form-inline\">\
	                				<select class=\"form-control\" data-bind=\"options: $root.months(), optionsText: 'label', optionsValue: 'value', value: $root.newMonth\"></select>\
                                	<select class=\"form-control\" data-bind=\"options: $root.years(), value: $root.newYear\"></select>\
									<button type=\"button\" class=\"btn btn-success\" data-bind=\"click: $root.createMonthEntries\">\
										<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>\
									</button>\
	                			</form>\
                            </th>\
                        </tr>\
                    </thead>\
                    <tbody data-bind=\"template: {name: 'ko_budgetGrid_row', if: categoryTree, data: categoryTree}\">\
                    </tbody>\
                </table>\
               </div>");
	templateEngine
	        .addTemplate(
	                "ko_budgetGrid_row",
	                "\
			<tr>\
				<td data-bind=\"text: text\"></td>\
				<td data-bind=\"template: {name: 'ko_budgetGrid_cell', data: $root.getData(id, 'base')}\">\
				</td>\
				<!-- ko foreach: $root.columnsOnCurrentPage -->\
					<td data-bind=\"template: {name: 'ko_budgetGrid_cell', data: $root.getData($parent.id, $data)}\">\
					</td>\
				<!-- /ko -->\
				<td>&nbsp;</td>\
			</tr>\
			<!-- ko if: 'nodes' in $data -->\
				<!-- ko template: {name: 'ko_budgetGrid_row', foreach: nodes} -->\
				<!-- /ko -->\
			<!-- /ko -->");
	templateEngine
	        .addTemplate(
	                "ko_budgetGrid_cell",
	                "\
    				<!-- ko if: $data -->\
    				<div data-bind=\"visible: !budgetAmount.isEditing()\">\
						<span data-bind=\"text: budgetAmount\"></span>\
						<a class=\"btn\" data-bind=\"click: budgetAmount.beginEdit\">\
							<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>\
						</a>\
					</div>\
    				<div class=\"input-group input-group-sm\" data-bind=\"visible: budgetAmount.isEditing\">\
    					<input class=\"form-control\" data-bind=\"value: budgetAmount\" />\
    					<span class=\"input-group-btn\">\
							<button type=\"button\" class=\"btn btn-danger\" data-bind=\"click: budgetAmount.cancelEdit\">\
								<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>\
							</button>\
							<button type=\"button\" class=\"btn btn-success\" data-bind=\"click: function() {$root.updateEntry($data); budgetAmount.endEdit();}\">\
								<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>\
							</button>\
						</span>\
					</div>\
					<span data-bind=\"text: actualAmount\"></span>\
    				<!-- /ko -->\
    				<!-- ko ifnot: $data -->\
					<button class=\"btn btn-success\">\
    					<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>\
					</button>\
    				<!-- /ko -->");
	templateEngine
	        .addTemplate(
	                "ko_budgetGrid_pageLinks",
	                "\
			<div class=\"row\">\
				<div class=\"btn-group pull-right budgetGridButtons\">\
					<button class=\"btn btn-default\" type=\"button\" data-bind=\"click: function() { $root.currentPageIndex($root.currentPageIndex() - 1) }, enable: currentPageIndex() > 0\">\
						<span class=\"glyphicon glyphicon-chevron-left\"></span>\
					</button>\
					<!-- ko foreach: ko.utils.range(0, maxPageIndex) -->\
						<button class=\"btn btn-default\" type=\"button\" data-bind=\"text: $data + 1, click: function() { $root.currentPageIndex($data) }, css: { active: $data == $root.currentPageIndex() }\">\
						</button>\
					<!-- /ko -->\
					<button class=\"btn btn-default\" type=\"button\" data-bind=\"click: function() { $root.currentPageIndex($root.currentPageIndex() + 1) }, enable: currentPageIndex() < maxPageIndex()\">\
						<span class=\"glyphicon glyphicon-chevron-right\"></span>\
					</button>\
				</div>\
			</div>");

	// The "budgetGrid" binding
	ko.bindingHandlers.budgetGrid = {
	    init : function() {
		    return {
			    'controlsDescendantBindings' : true
		    };
	    },
	    // This method is called to initialize the node, and will also be called again if you change what the grid is
		// bound to
	    update : function(element, viewModelAccessor, allBindings) {
		    var viewModel = viewModelAccessor();

		    // Empty the element
		    while (element.firstChild)
			    ko.removeNode(element.firstChild);

		    // Allow the default templates to be overridden
		    var gridTemplateName = "ko_budgetGrid_grid", rowTemplateName = "ko_budgetGrid_row", pageLinksTemplateName = "ko_budgetGrid_pageLinks";

		    // Render the main grid
		    var gridContainer = element.appendChild(document.createElement("DIV"));
		    ko.renderTemplate(gridTemplateName, viewModel, {
			    templateEngine : templateEngine
		    }, gridContainer, "replaceNode");

		    // Render the page links
		    var pageLinksContainer = element.appendChild(document.createElement("DIV"));
		    ko.renderTemplate(pageLinksTemplateName, viewModel, {
			    templateEngine : templateEngine
		    }, pageLinksContainer, "replaceNode");
	    }
	};
})();