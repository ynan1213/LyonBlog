/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.shiro.crypto;

import javax.crypto.spec.GCMParameterSpec;
import java.security.spec.AlgorithmParameterSpec;

/**
 * {@code CipherService} using the {@code AES} cipher algorithm for all encryption, decryption, and key operations.
 * <p/>
 * The AES algorithm can support key sizes of {@code 128}, {@code 192} and {@code 256} bits<b>*</b>.  This implementation
 * defaults to 128 bits.
 * <p/>
 * Note that this class retains changes the parent class's default {@link OperationMode#CBC CBC} mode to {@link OperationMode#GCM GCM} of operation
 * instead of the typical JDK default of {@link OperationMode#ECB ECB}.  {@code ECB} should not be used in
 * security-sensitive environments because {@code ECB} does not allow for initialization vectors, which are
 * considered necessary for strong encryption.  See the {@link DefaultBlockCipherService parent class}'s JavaDoc and the
 * {@link JcaCipherService JcaCipherService} JavaDoc for more on why the JDK default should not be used and is not
 * used in this implementation.
 * <p/>
 * <b>*</b> Generating and using AES key sizes greater than 128 require installation of the
 * <a href="http://java.sun.com/javase/downloads/index.jsp">Java Cryptography Extension (JCE) Unlimited Strength
 * Jurisdiction Policy files</a>.
 *
 * @since 1.0
 */
public class AesCipherService extends DefaultBlockCipherService {

    private static final String ALGORITHM_NAME = "AES";

    /**
     * Creates a new {@link CipherService} instance using the {@code AES} cipher algorithm with the following
     * important cipher default attributes:
     * <table>
     * <tr>
     * <th>Attribute</th>
     * <th>Value</th>
     * </tr>
     * <tr>
     * <td>{@link #setKeySize keySize}</td>
     * <td>{@code 128} bits</td>
     * </tr>
     * <tr>
     * <td>{@link #setBlockSize blockSize}</td>
     * <td>{@code 128} bits (required for {@code AES}</td>
     * </tr>
     * <tr>
     * <td>{@link #setMode mode}</td>
     * <td>{@link OperationMode#GCM GCM}<b>*</b></td>
     * </tr>
     * <tr>
     * <td>{@link #setPaddingScheme paddingScheme}</td>
     * <td>{@link PaddingScheme#NONE NoPadding}***</td>
     * </tr>
     * <tr>
     * <td>{@link #setInitializationVectorSize(int) initializationVectorSize}</td>
     * <td>{@code 128} bits</td>
     * </tr>
     * <tr>
     * <td>{@link #setGenerateInitializationVectors(boolean) generateInitializationVectors}</td>
     * <td>{@code true}<b>**</b></td>
     * </tr>
     * </table>
     * <p/>
     * <b>*</b> The {@link OperationMode#GCM GCM} operation mode is used instead of the JDK default {@code ECB} to
     * ensure strong encryption.  {@code ECB} should not be used in security-sensitive environments - see the
     * {@link DefaultBlockCipherService DefaultBlockCipherService} class JavaDoc's &quot;Operation Mode&quot; section
     * for more.
     * <p/>
     * <b>**</b>In conjunction with the default {@code GCM} operation mode, initialization vectors are generated by
     * default to ensure strong encryption.  See the {@link JcaCipherService JcaCipherService} class JavaDoc for more.
     * <p/>
     * <b>**</b>Since {@code GCM} is a stream cipher, padding is implemented in the operation mode and an external padding scheme
     * cannot be used in conjunction with {@code GCM}. In fact, {@code AES/GCM/PKCS5Padding} is just an alias in most JVM for
     * {@code AES/GCM/NoPadding}.
     */
    public AesCipherService() {
        super(ALGORITHM_NAME);
        setMode(OperationMode.GCM);
        setStreamingMode(OperationMode.GCM);
        setPaddingScheme(PaddingScheme.NONE);
    }

    @Override
    protected AlgorithmParameterSpec createParameterSpec(byte[] iv, boolean streaming) {

        if ((streaming && OperationMode.GCM.name().equals(getStreamingModeName()))
        || (!streaming && OperationMode.GCM.name().equals(getModeName()))) {
            return new GCMParameterSpec(getKeySize(), iv);
        }

        return super.createParameterSpec(iv, streaming);
    }
}
