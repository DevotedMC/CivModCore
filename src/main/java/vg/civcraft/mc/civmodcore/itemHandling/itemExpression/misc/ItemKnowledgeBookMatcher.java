package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ameliorate
 */
public class ItemKnowledgeBookMatcher implements ItemMatcher {
	public ItemKnowledgeBookMatcher(NameMatcher recipeMatcher, boolean requireAllMatch) {
		this.recipeMatcher = recipeMatcher;
		this.requireAllMatch = requireAllMatch;
	}

	public ItemKnowledgeBookMatcher(NameMatcher recipeMatcher) {
		this(recipeMatcher, false);
	}

	public static ItemKnowledgeBookMatcher construct(Optional<NameMatcher> recipeMatcher, boolean requireAllMatch) {
		return recipeMatcher.map((aRecipeMatcher) -> new ItemKnowledgeBookMatcher(aRecipeMatcher, requireAllMatch))
				.orElse(null);
	}

	public NameMatcher recipeMatcher;
	public boolean requireAllMatch;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof KnowledgeBookMeta) ||
				!((KnowledgeBookMeta) item.getItemMeta()).hasRecipes())
			return false;

		List<String> recipes = ((KnowledgeBookMeta) item.getItemMeta()).getRecipes().stream()
				.map(NamespacedKey::toString).collect(Collectors.toList());

		Stream<String> recipesStream = recipes.stream();

		if (requireAllMatch)
			return recipesStream.allMatch(recipeMatcher::matches);
		else
			return recipesStream.anyMatch(recipeMatcher::matches);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof KnowledgeBookMeta))
			item.setType(Material.KNOWLEDGE_BOOK);

		KnowledgeBookMeta meta = (KnowledgeBookMeta) item.getItemMeta();

		List<NamespacedKey> recipes = meta.getRecipes();
		if (requireAllMatch)
			recipes.clear();

		String defaultValue = recipes.isEmpty() ? "" : recipes.get(0).toString();
		String[] solved = recipeMatcher.solve(defaultValue).split(":", 2);
		String namespace = solved[0];
		String str = solved[1];

		recipes.add(new NamespacedKey(namespace, str));

		meta.setRecipes(recipes);
		item.setItemMeta(meta);
		return item;
	}
}
