package com.kbit.utilities.filewatcher;

import com.kbit.domain.types.KFile;

public interface Watchable {

	void onChange(final KFile file);
	
}
