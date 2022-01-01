package com.sbrf.reboot.repository;

import com.sbrf.reboot.exceptions.FileAccountRepositoryException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Database file format:
 * client1:contractNumber1,contractNumber2...
 * client2:...
 * <p>
 * All public methods throws {@link FileAccountRepositoryException}
 */
public class FileAccountRepository implements AccountRepository {
    private static final String FILE_NAME = "Accounts.txt";

    @Override
    public Set<Long> getAllAccountsByClientId(long clientId) {
        String prefix = String.valueOf(clientId) + ':';
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_NAME)))) {
            String line;
            while ((line = reader.readLine()) != null) { // returns null in case of EOF
                if (line.startsWith(prefix)) {
                    return Arrays.stream(line.split(":")[1].split(",")).map(String::trim).map(Long::valueOf).collect(Collectors.toSet());
                }
            }
            return new HashSet<>(); // in case of EOF
        } catch (IOException e) { // FileInputStream constructor and readLine method
            throw new FileAccountRepositoryException(e);
        }
    }

    private boolean updateAccountInfoByClientId(File tempFile, long clientId, long oldContractNumber, long newContractNumber) throws IOException {
        String prefix = String.valueOf(clientId) + ':';
        try (RandomAccessFile db = new RandomAccessFile(FILE_NAME, "rwd");
             RandomAccessFile temp = new RandomAccessFile(tempFile, "rwd");
             FileChannel dbChannel = db.getChannel();
             FileChannel tempChannel = temp.getChannel()) {
            String line;

            // readLine() returns null in case of EOF
            do if ((line = db.readLine()) == null) return false;
            while (!line.startsWith(prefix));

            Matcher matcher = Pattern.compile("[:,](" + oldContractNumber + ")(,|$)").matcher(line);
            if (!matcher.find()) return false; // if not found
            MatchResult matchResult = matcher.toMatchResult();

            // checks for EOF (for last line)
            long current = db.getFilePointer();
            int offset = (db.read() == -1) ? 0 : 1;

            long offsetLineStart = current - line.length() - offset;
            long offsetStart = offsetLineStart + matchResult.start(1);
            long offsetEnd = offsetLineStart + matchResult.end(1);
            long transferredContentSize = dbChannel.size() - offsetEnd;

            dbChannel.transferTo(offsetEnd, transferredContentSize, tempChannel);
            dbChannel.truncate(offsetStart);
            db.seek(offsetStart);
            db.write(String.valueOf(newContractNumber).getBytes(StandardCharsets.UTF_8));
            long newOffset = db.getFilePointer();
            tempChannel.position(0L);
            dbChannel.transferFrom(tempChannel, newOffset, transferredContentSize);
            return true;
        }
    }

    @Override
    public boolean updateAccountInfoByClientId(long clientId, long oldContractNumber, long newContractNumber) {
        try {
            File tempFile = Files.createTempFile(FILE_NAME, ".tmp").toFile();
            boolean result = updateAccountInfoByClientId(tempFile, clientId, oldContractNumber, newContractNumber);
            Files.delete(tempFile.toPath());
            return result;
        } catch (IOException e) {
            throw new FileAccountRepositoryException(e);
        }
    }

    @Override
    public Set<Long> getAllCardsByAccount(long contractNumber) {
        throw new FileAccountRepositoryException("Not Implemented");
    }
}
