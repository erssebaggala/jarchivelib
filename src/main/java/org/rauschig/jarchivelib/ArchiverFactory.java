/**
 *    Copyright 2013 Thomas Rausch
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.rauschig.jarchivelib;

/**
 * Factory for creating {@link Archiver} instances by a given archiver type name. Use the constants in this class to
 * pass to the factory method.
 */
public final class ArchiverFactory {

    private ArchiverFactory() {

    }

    /**
     * Creates an Archiver for the given archive format that uses compression.
     * 
     * @param archiveFormat the archive format e.g. "tar" or "zip"
     * @param compression the compression algorithm name e.g. "gz"
     * 
     * @return a new Archiver instance that also handles compression
     * @throws IllegalArgumentException if the archive format or the compression type is unknown
     */
    public static Archiver createArchiver(String archiveFormat, String compression) throws IllegalArgumentException {
        if (!ArchiveFormat.isValidArchiveFormat(archiveFormat)) {
            throw new IllegalArgumentException("Unknown archive format " + archiveFormat);
        }
        if (!CompressionType.isValidCompressionType(compression)) {
            throw new IllegalArgumentException("Unknown compression type " + compression);
        }

        CommonsArchiver archiver = new CommonsArchiver(archiveFormat);
        CommonsCompressor compressor = new CommonsCompressor(compression);

        return new ArchiverCompressorDecorator(archiver, compressor);
    }

    /**
     * Creates an Archiver for the given archive format that uses compression.
     * 
     * @param archiveFormat the archive format
     * @param compression the compression algorithm
     * 
     * @return a new Archiver instance that also handles compression
     */
    public static Archiver createArchiver(ArchiveFormat archiveFormat, CompressionType compression) {
        return createArchiver(archiveFormat.getName(), compression.getName());
    }

    /**
     * Creates an Archiver for the given archive format.
     * 
     * @param archiveFormat the archive format e.g. "tar" or "zip"
     * 
     * @return a new Archiver instance
     * @throws IllegalArgumentException if the archive format is unknown
     */
    public static Archiver createArchiver(String archiveFormat) throws IllegalArgumentException {
        if (!ArchiveFormat.isValidArchiveFormat(archiveFormat)) {
            throw new IllegalArgumentException("Unknown archive format " + archiveFormat);
        }

        return new CommonsArchiver(archiveFormat);
    }

    /**
     * Creates an Archiver for the given archive format.
     * 
     * @param archiveFormat the archive format
     * 
     * @return a new Archiver instance
     */
    public static Archiver createArchiver(ArchiveFormat archiveFormat) {
        return new CommonsArchiver(archiveFormat.getName());
    }

}
