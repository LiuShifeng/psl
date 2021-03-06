/*
 * This file is part of the PSL software.
 * Copyright 2011-2015 University of Maryland
 * Copyright 2013-2015 The Regents of the University of California
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.umd.cs.psl.ui.functions.textsimilarity;

import com.wcohen.ss.BasicStringWrapper;
import com.wcohen.ss.Jaccard;

import edu.umd.cs.psl.database.ReadOnlyDatabase;
import edu.umd.cs.psl.model.argument.ArgumentType;
import edu.umd.cs.psl.model.argument.GroundTerm;
import edu.umd.cs.psl.model.argument.StringAttribute;
import edu.umd.cs.psl.model.function.ExternalFunction;

/**
 * Wraps the Jaccard string similarity from the Second String library.
 * If the similarity is below a threshold (default=0.5) it returns 0.
 */
class JaccardSimilarity implements ExternalFunction {
	// similarity threshold (default=0.5)
	private double simThresh;

	// constructors
	public JaccardSimilarity() {
		this.simThresh = 0.5;
	}
	public JaccardSimilarity(double simThresh) {
		this.simThresh = simThresh;
	}
	
	@Override
	public int getArity() {
		return 2;
	}

	@Override
	public ArgumentType[] getArgumentTypes() {
		return new ArgumentType[] { ArgumentType.String, ArgumentType.String };
	}
	
	@Override
	public double getValue(ReadOnlyDatabase db, GroundTerm... args) {
		String a = ((StringAttribute) args[0]).getValue();
		String b = ((StringAttribute) args[1]).getValue();
		BasicStringWrapper aWrapped = new BasicStringWrapper(a);
		BasicStringWrapper bWrapped = new BasicStringWrapper(b);
		
		Jaccard jaccard = new Jaccard();
		double sim = jaccard.score(aWrapped, bWrapped);
		
		if (sim < simThresh) 
			return 0.0;
		else 
			return sim;
    }
}
