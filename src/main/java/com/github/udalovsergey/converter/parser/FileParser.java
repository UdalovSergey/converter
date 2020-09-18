package com.github.udalovsergey.converter.parser;

import java.io.File;
import java.util.stream.Stream;

public interface FileParser {

    Stream<ParserResult> parse(File file);

}
