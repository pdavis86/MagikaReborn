package magikareborn.compat.jei;

import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public interface ICustomRecipeCategory<R> extends IRecipeCategory, IRecipeWrapperFactory<R> {

    Class getRecipeClass();

    String getRecipeCategoryUid();
}
