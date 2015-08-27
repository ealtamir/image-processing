package ar.com.itba.image_actions.noise;

import ar.com.itba.operations.ImageTransform;
import ar.com.itba.utils.random.RandomGenerator;

public interface NoiseGenerator extends ImageTransform {

	NoiseGenerator setRandomGenerator(RandomGenerator generator);

}
