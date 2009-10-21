package org.gephi.preview.api.color.colorizer;

/**
 * Interface of an edge colorizer.
 *
 * @author Jérémy Subtil <jeremy.subtil@gephi.org>
 */
public interface EdgeColorizer extends Colorizer {

	/**
	 * Colors the given client.
	 *
	 * @param client  the client to color
	 */
    public void color(EdgeColorizerClient client);
}
