package nz.co.anzac.moneymanager.util;

import nz.co.anzac.moneymanager.model.Category;
import nz.co.anzac.moneymanager.representation.TreeNode;

public class TreeNodeUtil {
	public static TreeNode toTreeNode(final Category category) {
		if (category == null) {
			return null;
		}
		final TreeNode node = new TreeNode();
		node.setId(category.getId());
		node.setBackColor(category.getColour());
		node.setText(category.getName());
		node.setSelectable(true);
		node.getState().setExpanded(category.getSubCategories() != null && !category.getSubCategories().isEmpty());
		category.getSubCategories().forEach(c -> {
			node.getNodes().add(toTreeNode(c));
		});
		return node;
	}
}
