package ${package}.pages.add;


import ${package}.pages.Add;

public abstract class CustomAdd<T> extends Add
{
	public abstract Class<T> getType();

	protected void onActivate() throws Exception
	{
		super.activate(getType());
	}
}
